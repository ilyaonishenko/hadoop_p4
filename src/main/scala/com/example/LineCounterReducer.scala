package com.example

import java.lang

import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Reducer
import scala.collection.JavaConverters._

class LineCounterReducer extends Reducer[Text, Text, Text, Text]{

  override def reduce(key: Text, values: lang.Iterable[Text], context: Reducer[Text, Text, Text, Text]#Context): Unit = {
    for(value <- values.asScala){
      context.write(key, value)
    }
  }
}
