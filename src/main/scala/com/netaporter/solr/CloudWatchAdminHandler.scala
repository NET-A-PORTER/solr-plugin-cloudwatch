package com.netaporter.solr

import org.apache.solr.common.params.CoreAdminParams
import org.apache.solr.core.CoreContainer
import org.apache.solr.handler.admin.CoreAdminHandler
import com.amazonaws.metrics.AwsSdkMetrics
import org.apache.solr.request.SolrQueryRequest
import org.apache.solr.response.SolrQueryResponse

class CloudWatchAdminHandler(coreContainer: CoreContainer) extends CoreAdminHandler(coreContainer) {

  def this() = this(null)

  val metricsEnabled = CloudWatch.ensureMetricsSetup()

  override def handleRequestBody(req: SolrQueryRequest, res: SolrQueryResponse) = {
    val action = req.getParams.get(CoreAdminParams.ACTION)
    if ("cloudwatch".equalsIgnoreCase(action)) {
      res.add("metricsEnabled", metricsEnabled)
      if (metricsEnabled) {
        res.add("hostMetricName", AwsSdkMetrics.getHostMetricName)
        res.add("metricNamespace", AwsSdkMetrics.getMetricNameSpace)
        res.add("singleMetricNamespace", AwsSdkMetrics.isSingleMetricNamespace)
        res.add("perHostMetricsIncluded", AwsSdkMetrics.isPerHostMetricIncluded)
      }
    } else {
      super.handleRequestBody(req, res)
    }
  }
}