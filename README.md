# DAL

## Verwendung

```**Alle Klassen sind darauf ausgelegt, dass alle Generic Types definiert werden, ansonsten kann die funktionalität nicht garantiert werden!**```

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

}
```
### Pipelines
Pipelines benutzen Adapter,wenn die Daten für die Datasource in Bytes konvertert werden müssen ggf. um Daten für ein Responses aus den Bytes wiederzugewinnen. Unterschieden wird hier zwischen einer Read- und einer Writepipeline. Eine Pipeline kann zwar beide Interfaces implementieren, muss es aber nicht.

```java
// Bei der Readpipeline wird durch die Generics T und V angegeben, welcher Request gehandelt wird und welcher Response dabei zurückkommt.
// Bei der Writepipeline wird durch D definiert, welchen Writerequests die Pipeline bearbeiten kann
class MyPipelineClass implements ReadPipeline<T extends DALReadRequest,V extends DALResponse>, WritePipeline<D extends DALWriteRequest> {

    void writeData(D writeRequest){
        // Hier werden die Daten eines Requests gehandhabt.
        // Üblich wäre es, hier eine Datasource zu verwenden.
        // Diese kann von der Datasourcelibrary geholt werden,
        // dafür kann die get getDatasourceClass() Methode des Requests verwendet werden.
    }

    V readData(T readRequest){
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
// als Type <T> wird angegeben, von welchem Typ die Datasource ist
class MyReadRequestClass implements DALReadRequest<T extends DataSource> {

    String getIdentifier(){
        // Der Identifier der gesuchten Daten
    }

}
```
### DALResponse
```
// als Type <T> wird angegeben, was für ein Typ der Wert des Responses ist
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

    T getData(String identifier){
        // die Datasource soll einen mit dem identifier verbundenen Wert zurückgeben
    }

    void writeData(String key, T value){
        // die gegebenen bytes sollen unter dem angegebenen Identifier gespeichert werden
    }

}
```

## Beispiel anhand eines Config-Systems
### ReadRequest
```java
public class ConfigReadRequest implements DALReadRequest<ConfigDB> {

    private final String configKey;

    public ConfigReadRequest(String configKey){
        this.configKey = configKey;
    }

    @Override
    public String getIdentifier() {
        return this.configKey;
    }
}
```
### WriteRequest
```java
public class ConfigWriteRequest implements DALWriteRequest<ConfigDB,String> {

    private final String key;
    private final String value;

    public ConfigWriteRequest(String key, String value) {
        this.key = key;
        this.value = value;
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
public class DefaultConfigPipeline implements
        ReadPipeline<ConfigReadRequest, ConfigResponse>, WritePipeline<ConfigWriteRequest> {

    @Override
    public ConfigResponse readBytes(ConfigReadRequest readRequest) {
        return new ConfigResponse(DatasourceLibrary.get()
                .getDatasource(readRequest.getDatasourceClass())
                .getData(readRequest.getIdentifier()));
    }

    @Override
    public void writeBytes(ConfigWriteRequest writeRequest) {
        DatasourceLibrary.get().getDatasource(writeRequest.getDatasourceClass())
                .writeData(writeRequest.getKey(), writeRequest.getData());
    }

}
```
### Datasource (Postgres)
```java
public class ConfigDB implements DataSource<String> {


    private final ConfigRepository configRepository = new ConfigRepository();

    @Override
    public String getData(String identifier) {
        return configRepository.getValue(identifier);
    }

    @Override
    public void writeData(String key, String value) {
        configRepository.saveValue(key, value);
    }


}
```

### Das Repository
``` java
public class ConfigRepository {

    Repository<ConfigEntry> configEntryRepository = Repository.create(ConfigEntry.class);

    public String getValue(String key){
        return this.configEntryRepository.query().filter(new MatchFilter("key",key))
                .database().findAll().get(0).getValue();
    }

    public void saveValue(String key, String value){
        this.configEntryRepository.query().create(new ConfigEntry(key, value));
    }

}
```
### Das Model
```java
@AllArgsConstructor
@Getter
public class ConfigEntry {

    @PrimaryKey
    String key;
    String value;

}
```
### Response
```java
public record ConfigResponse(String data) implements DALResponse<String> { }
```

### Usage
```java
DAL.get().save(new ConfigWriteRequest("mykey","myvalue"));
```
```java
ConfigResponse response = DAL.get().read(new ConfigReadRequest("mykey"));
```
### ORM (Evelon)
Die ist eine Library, welche aus Klassen Datenbanktabellen (3. Normalform) generiert. Dies bietet sich besonders für Datasources an.
Die Nutzung ist in der Datasource des Beispiel zu erkennen.

[Offizielle Dokumentation](https://github.com/ByteMCNetzwerk/evelon/wiki)
