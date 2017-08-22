package com.example

import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

class LineCounterMapper extends Mapper[LongWritable, Text, Text, Text]{

  override def map(key: LongWritable, value: Text, context: Mapper[LongWritable, Text, Text, Text]#Context): Unit = {
    val line = value.toString.split("\t", 2)
    context.write(new Text(line(0)), new Text(line(1)))
  }
}
