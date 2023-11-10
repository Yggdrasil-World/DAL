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

### Datasources

### ORM (Evelon)

[Offizielle Dokumentation](https://github.com/ByteMCNetzwerk/evelon/wiki)
