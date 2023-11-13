# DAL

## Verwendung

### Adapter
Adapter werden verwendet um Objecte in Bytes umzuwandeln bzw. von Bytes wieder in ein Object. Damit ein Adapter automatisch gefunden werden kann, muss die Klasse einen no-Args Constructor haben.

```java
// als Type <T> wird angegeben, für welche Klasse dieser Adapter fungiert
class MyAdapterClass implements Adapter<T> {

    byte[] convertToData(T object){
        // Hier wird das Object in Bytes umgewandelt, welche an die Datenquelle weitergegeben wird.
    }

    T createFromData(byte[] data){
        //  Diese Methode baut aus den Bytes wieder das jeweilige Object
    }

    Class<T> getClassOfAdapter(){
        // Hier wird einfach nur die Klasse vom Typ von dem T ist, zurückgegeben.
    }
}
```
### Pipelines
Pipelines benutzen in der Regel Adapter um Requests zu verarbeiten und ggf. Responses zu senden.

```java
class MyPipelineClass implements Pipeline {

    Class<DALRequest>[] applyForRequestTypes(){
        // Dies gibt ein Array an Classes vom Supertyp "DALRequest" zurück.
        // Diese werden verwendet um solche Anfragen an die Pipeline weiterzuleiten.
        // Jeder Requesttype kann nur einmal registriert werden.
        // Es stehen sowol Read-, als auch Writerequests drin.
    }

    void writeBytes(DALWriteRequest writeRequest){
        // Hier werden die Daten eines Requests gehandhabt.
        // Üblich wäre es, hier eine Datasource zu verwenden.
    }

    DALResponse readBytes(DALReadRequest readRequest){
        // Hiermit wird das lesen von Daten umgesetzt.
        // Auch hier würde man von einer Datasource lesen.
    }

}
```
### DALRequest

Requests werden benutzt, um mit der DAL-API zu kommunizieren.
#### DALWriteRequest
Um Daten speichern zu lassen, wird ein DALWriteRequest genutzt.
```java
// als Type <T> wird angegeben, von welchem Typ die Datasource ist
// als Type <V> wird angegeben, für welche Klasse dieser Request genutzt wird
class MyWriteRequestClass implements DALWriteRequest<T extends DataSource,V>{

    DALWriteScope getScope(){
        // Hier wird angegebn, welcher Writescope geforder wird (siehe unten bei Datasources)
    }

    T getDataSource(){
        // Hier wird angegeben, welche Datasource verwendet werden soll.
    }

    String getKey(){
        // Der Key, der zum Speichern der Daten verwendet werden soll
    }

    V getData(){
        // Die Daten, die gespeichert werden sollen
    }

}
```

#### DALReadRequest
Wenn Daten gelesen werden sollen, wird ein DalReadRequest genutzt. Bei einem solchen bekommt man immer ein Objekt vom Typ DALResponse zurück.
```
class MyReadRequestClass implements DALReadRequest {

    Class<? extends DataSource> getDatasource(){
        // Die Datasource, von der gelesen werden soll
    }

    String getIdentifier(){
        // Der Identifier der gesuchten Daten
    }

    Class getTargetType(){
        // Der Datentyp der Zieldaten
    }

}
```
### DALResponse
```
// als Type <V> wird angegeben, was für ein Typ der Wert des Responses ist
class MyResponseClass implements DALResponse<T> {

    T getData(){
        // gibt das Ergebnis des Responses zurück
    }

}
```
### Datasources
Datasources können sowohl persistente (z.B. MySQL-Datenbank) als auch In-Memory Speicher (z.B. RabbitMQ) sein.
Wie auch die Adapter werden Datasources automatisch gefunden, solange sie public sind und einen no-Args Constrcutor haben.
```java
class MyDataSourceClass implements DataSource {

    DALWriteScope[] getSupportedWriteScopes(){
        // Unterstütze Scopes sind: RUNTIME, RUNTIME_SYNCHRONISED, PERSISTENT, PERSISTENT_SYNCHRONISED
    }

    byte[] getBytes(String identifier){
        // die Datasource soll einen mit dem identifier verbundenen Wert zurückgeben
    }

    void writeBytes(String key, byte[] value){
        // die gegebenen bytes sollen unter dem angegebenen Identifier gespeichert werden
    }

}
```

## Beispiel anhand eines Config-Systems
### ReadRequest
```java
public class DALConfigReadRequest implements DALReadRequest{

    private final String configKey;

    public DALConfigReadRequest(String configKey){
        this.configKey = configKey;
    }

    @Override
    public Class<? extends DataSource> getDatasource() {
        return ConfigDB.class;
    }

    @Override
    public String getIdentifier() {
        return this.configKey;
    }

    @Override
    public Class getTargetType() {
        return String.class;
    }
}
```
### WriteRequest
```java
public class DALConfigWriteRequest implements DALWriteRequest<ConfigDB,String>{

    private final String key;
    private final String value;

    public DALConfigWriteRequest(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public DALWriteScope getScope() {
        return DALWriteScope.PERSISTENT;
    }

    @Override
    public ConfigDB getDataSource() {
        return (ConfigDB) DatasourceLibrary.get().getDatasource(ConfigDB.class);
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getData() {
        return this.value;
    }
}
```

### Pipeline
```java
public class DefaultConfigPipeline implements Pipeline {

    @Override
    public Class<DALRequest>[] applyForRequestTypes() {
        return new Class[]{DALConfigReadRequest.class,DALConfigWriteRequest.class};
    }

    @Override
    public void writeBytes(DALWriteRequest writeRequest) {
        DALConfigWriteRequest dalConfigWriteRequest = (DALConfigWriteRequest) writeRequest;
        dalConfigWriteRequest.getDataSource().writeBytes(dalConfigWriteRequest.getKey(),dalConfigWriteRequest.getData().getBytes());
    }

    @Override
    public DALResponse readBytes(DALReadRequest readRequest) {
        String configValue = new String(DatasourceLibrary.get().getDatasource(
                readRequest.getDatasource()).getBytes(readRequest.getIdentifier()), StandardCharsets.UTF_8);
        return new DALConfigResponse(configValue);
    }
}
```
### Datasource (Postgres)
```java
public class ConfigDB implements DataSource {


    private final Connection connection;
    private final HashMap<String, PreparedStatement> preparedStatements = new HashMap<>();

    public ConfigDB() {
        try {
            connection = setupConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.initialSetup();
    }

    private Connection setupConnection() throws SQLException {
        var DB_SERVER = System.getProperty(DatabaseStrings.DatabaseEnv.ConfigDB.DB_SERVER);
        var DB_PORT = System.getProperty(DatabaseStrings.DatabaseEnv.ConfigDB.DB_PORT);
        var DB_USER = System.getProperty(DatabaseStrings.DatabaseEnv.ConfigDB.DB_USER);
        var DB_PASSWORD = System.getProperty(DatabaseStrings.DatabaseEnv.ConfigDB.DB_PASSWORD);
        return DriverManager.getConnection(DatabaseStrings.CONNECTION_STRING
                .formatted(DatabaseStrings.Databases.POSTGRES, DB_SERVER, DB_PORT), DB_USER, DB_PASSWORD);
    }

    private void initialSetup() {
        try {
            this.createStatements();
            this.createTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createStatements() throws SQLException {
        this.preparedStatements.put(StatementQuerries.CREATE_SCHEMA,
                this.connection.prepareStatement(StatementQuerries.CREATE_SCHEMA));
        this.preparedStatements.put(StatementQuerries.CREATE_TABLE,
                this.connection.prepareStatement(StatementQuerries.CREATE_TABLE));
        this.preparedStatements.put(StatementQuerries.SELECT_VALUE,
                this.connection.prepareStatement(StatementQuerries.SELECT_VALUE));
        this.preparedStatements.put(StatementQuerries.INSERT_VALUE,
                this.connection.prepareStatement(StatementQuerries.INSERT_VALUE));
    }

    private void createTables() throws SQLException {
        this.preparedStatements.get(StatementQuerries.CREATE_SCHEMA).execute();
        this.preparedStatements.get(StatementQuerries.CREATE_TABLE).execute();
    }

    @Override
    public DALWriteScope[] getSupportedWriteScopes() {
        return new DALWriteScope[]{DALWriteScope.PERSISTENT};
    }

    @Override
    public byte[] getBytes(String identifier) {
        try {
            this.preparedStatements.get(StatementQuerries.SELECT_VALUE).setString(1, identifier);
            ResultSet resultSet = this.preparedStatements.get(StatementQuerries.SELECT_VALUE).executeQuery();
            resultSet.next();
            String result = resultSet.getString("value");
            return result.getBytes(StandardCharsets.UTF_8);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public void writeBytes(String key, byte[] value) {
        try {
            PreparedStatement statement = this.preparedStatements.get(StatementQuerries.INSERT_VALUE);
            statement.setString(1, key);
            statement.setString(2, new String(value, StandardCharsets.UTF_8));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private interface StatementQuerries {

        String schema = System.getProperty(DatabaseStrings.DatabaseEnv.ConfigDB.DB_SCHEMA);
        String CREATE_SCHEMA = "CREATE SCHEMA IF NOT EXISTS %s".formatted(schema);
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s.config_values (key TEXT PRIMARY KEY, value TEXT)".formatted(schema);
        String SELECT_VALUE = "SELECT value FROM %s.config_values WHERE key = ?".formatted(schema);
        String INSERT_VALUE = "INSERT INTO %s.config_values (key, value) VALUES (?,?)".formatted(schema);
    }

}
```
### Response
```java
public class DALConfigResponse implements DALResponse<String> {

    private final String data;

    public DALConfigResponse(String data) {
        this.data = data;
    }

    @Override
    public String getData() {
        return this.data;
    }

}
```

### Useage
```java
DAL.get().save(new DALConfigWriteRequest("Key", "Value"));
```
```java
String value = (String) DAL.get().read(new DALConfigReadRequest("Key")).getData();
```
### ORM (Evelon)

[Offizielle Dokumentation](https://github.com/ByteMCNetzwerk/evelon/wiki)
