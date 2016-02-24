package com.netaporter.solr

import com.amazonaws.metrics.AwsSdkMetrics
import org.slf4j.LoggerFactory

import scala.util.Try

object CloudWatch {

  val log = LoggerFactory.getLogger(getClass)

  // Use a static lazy val to ensure that init() is only called once per JVM
  lazy val metricsEnabled = init()
  def ensureMetricsSetup(): Boolean = metricsEnabled

  def sysPropBoolean(name: String, default: Boolean) =
    sys.props.get(name)
      .flatMap(strValue => Try(strValue.toBoolean).toOption)
      .getOrElse(default)

  private def init() = {
    log.info("Initialising CloudWatchMetrics Plugin")

    val hostMetricName = sys.props.get("awsmetrics.host-metric-name")
    val singleMetricNamespace = sysPropBoolean("awsmetrics.single-metric-namespace", default = true)
    val perHostMetricsIncluded = sysPropBoolean("awsmetrics.per-host-metrics-included", default = true)

    if(hostMetricName.isDefined) {
      AwsSdkMetrics.setHostMetricName(hostMetricName.get)
    }

    AwsSdkMetrics.setSingleMetricNamespace(singleMetricNamespace)
    AwsSdkMetrics.setPerHostMetricsIncluded(perHostMetricsIncluded)

    AwsSdkMetrics.enableDefaultMetrics()
  }
}