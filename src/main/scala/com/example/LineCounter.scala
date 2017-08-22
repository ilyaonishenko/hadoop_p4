package com.example

import org.apache.hadoop.conf.{Configuration, Configured}
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.{FileInputFormat, TextInputFormat}
import org.apache.hadoop.mapreduce.lib.output.{FileOutputFormat, TextOutputFormat}
import org.apache.hadoop.util.{Tool, ToolRunner}

object LineCounter extends Configured with Tool{

  def main(args: Array[String]): Unit = {
    println("Starting program")
    val res = ToolRunner.run(new Configuration, LineCounter, args)
    println("Stopping program")
    System.exit(res)
  }

  override def run(args: Array[String]): Int = {
    val conf = getConf

    if(args.length != 2){
      println("Number of input args is incorrect")
      return 2
    }

    val job = Job.getInstance(conf)

    job.setJarByClass(getClass)
    job.setJobName("LineCounter")

    job.setInputFormatClass(classOf[TextInputFormat])
    job.setOutputFormatClass(classOf[TextOutputFormat[Text, Text]])

    println("input path: "+args(0))
    println("output path: "+args(1))

    FileInputFormat.addInputPath(job, new Path(args(0)))
    FileOutputFormat.setOutputPath(job, new Path(args(1)))

    job.setMapperClass(classOf[LineCounterMapper])
    job.setCombinerClass(classOf[LineCounterReducer])
    job.setReducerClass(classOf[LineCounterReducer])

    job.setNumReduceTasks(6)

    job.setMapOutputKeyClass(classOf[Text])
    job.setMapOutputValueClass(classOf[Text])

    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[Text])

    job.waitForCompletion(true).compareTo(true)
  }
}
