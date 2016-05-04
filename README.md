Ananke
=======

Ananke is a Java library client that's required to interface with a 
[StatsD](https://github.com/etsy/statsd) server. Netuitive uses Ananke to communitate with our 
Netuitive-StatsD server that's built into our [Netuitive Linux agent](https://github.com/Netuitive/omnibus-netuitive-agent).

For more information on the Netuitive Linux Agent and the Netuitive-StatsD server, see the [help docs](https://help.netuitive.com/Content/Misc/Datasources/Netuitive/new_netuitive_datasource.htm)
or contact Netuitive support at [support@netuitive.com](mailto:support@netuitive.com).

Building Ananke
----------------

### Linux
```
./gradlew build
```
### Windows
```
gradlew.bat build
```