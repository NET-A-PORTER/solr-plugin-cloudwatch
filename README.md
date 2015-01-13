CloudWatch Solr Plugin
----------------------

This plugin will publish JVM heap and thread stats to CloudWatch as described [here](http://java.awsblog.com/post/Tx3C0RV4NRRBKTG/Enabling-Metrics-with-the-AWS-SDK-for-Java)

# Installation

1) Drop [`solr-plugin-cloudwatch-0.1.jar`](http://search.maven.org/remotecontent?filepath=com/netaporter/solr-plugin-cloudwatch/0.1/solr-plugin-cloudwatch-0.1.jar) into the directory specified by the `sharedLib` in your `solr.xml`

2) Add the following to your `solr.xml`

    <str name="adminHandler">com.netaporter.solr.CloudWatchAdminHandler</str>

3) Add the following system properties when you launch Solr, replacing `MySolrHost` with an identifier for this specific
host, and replacing `MySolrCluster` with an identifier for your Solr cluster.

    -Dawsmetrics.host-metric-name=MySolrHost
    -Dcom.amazonaws.sdk.enableDefaultMetrics=metricNameSpace=MySolrCluster,cloudwatchRegion=eu-west-1

You can use your EC2 Instance ID for the `awsmetrics.host-metric-name` system property like so:

    -Dawsmetrics.host-metric-name=`ec2-metadata -i | cut -d' ' -f2`

4) Restart Solr and check the installation is working by going to the following URL:

    $ curl 'http://localhost/admin/cores?action=CLOUDWATCH&wt=json'
    {"responseHeader":{"status":0,"QTime":0},"metricsEnabled":true,"hostMetricName":"i-1234abcd","metricNamespace":"MySolrCluster"}

You should now start stats appear in the CloudWatch web UI.