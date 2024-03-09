package fr.uge.ugerevevueandroid.information

data class UnitTestResultInformation(val testsTotalCount:Long,
                                     val testsSucceededCount:Long,
                                     val testsFailedCount:Long,
                                     val testsTotalTime:Long,
                                     val failures:String)
