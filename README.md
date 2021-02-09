Ananke
=======

Ananke is a Java library client that's required to interface with a 
[StatsD](https://github.com/etsy/statsd) server. CloudWisdom uses Ananke to communicate with our 
Netuitive-StatsD server that's built into our [CloudWisdom Linux agent](https://github.com/Netuitive/omnibus-netuitive-agent).

For more information on the CloudWisdom Linux Agent and the Netuitive-StatsD server, see the [help docs](https://docs.virtana.com/en/linux-agent.html)
or contact CloudWisdom support at [cloudwisdom.support@virtana.com](mailto:cloudwisdom.support@virtana.com).

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
