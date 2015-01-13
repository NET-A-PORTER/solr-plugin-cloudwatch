package com.netaporter.solr

import com.amazonaws.metrics.AwsSdkMetrics
import org.slf4j.LoggerFactory

object CloudWatch {

  val log = LoggerFactory.getLogger(getClass)

  // Use a static lazy val to ensure that init() is only called once per JVM
  lazy val metricsEnabled = init()
  def ensureMetricsSetup(): Boolean = metricsEnabled

  private def init() = {
    log.info("Initialising CloudWatchMetrics Plugin")

    val hostMetricName = sys.props.get("awsmetrics.host-metric-name")

    if(hostMetricName.isDefined) {
      AwsSdkMetrics.setHostMetricName(hostMetricName.get)
    }

    AwsSdkMetrics.setPerHostMetricsIncluded(true)

    AwsSdkMetrics.enableDefaultMetrics()
  }
}