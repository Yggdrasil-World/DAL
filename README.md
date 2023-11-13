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
Pipelines 

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
        //
    }

    T getDataSource(){
        //
    }

    String getKey(){
        //
    }

    V getData(){
        //
    }

}
```

#### DALReadRequest
Wenn Daten gelesen werden sollen, wird ein DalReadRequest genutzt. Bei einem solchen bekommt man immer ein Objekt vom Typ DALResponse zurück.
```
class MyReadRequestClass implements DALReadRequest {

    Class<? extends DataSource> getDatasource(){
        //
    }

    String getIdentifier(){
        //
    }

    Class getTargetType(){
        //
    }

}
```
### DALResponse
```
// als Type <V> wird angegeben, was für ein Typ der Wert des Responses ist
class MyResponseClass implements DALResponse<T> {

    T getData(){
        //
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

### ORM (Evelon)

[Offizielle Dokumentation](https://github.com/ByteMCNetzwerk/evelon/wiki)

### Requests

### Responses
