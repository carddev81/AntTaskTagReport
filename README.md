# AntTaskTagReport
ANT task used for generating a task tags report, ie. as an attachment to a completion email of a build process. Task Tags that are usually found in application source code can be TODO, FIXME, CLEANME, ect.

An example of how to implement this task into your project is as follows:
```
<typedef name="tasktagreport" classname="com.omo.tools.ant.TaskTagReportTask" onerror="report"/>
<if>
  <typefound name="tasktagreport"/>
    <then>
      <tasktagreport outFile="${src}\${application.name}_tasktag_report.html"
               applicationName="${application.name}"
               repository="${cvsRepository}"
               branchOrTagName="${CVS.BranchNameOrTagName}"
               buildName="${build.name}"
               format="html"
               dir="src"
               pattern="(^:\s|-->)">
        <include name="**/*.java"/>
        <include name="**/*.js"/>
        <include name="**/*.jsp"/>
      </tasktagreport>
    </then>
  <else>
    <echo>tasktagreport task cannot be used due Class Not Found:  com.omo.tools.ant.TaskTagReportTask</echo>
  </else>
</if>
```

---
The example above should give you a clear understanding of how to implement this into your build.xml.  A short explanation of the above scripting is first the typedef task is called for setting up the tasktagreport by attempting to load the com.omo.tools.ant.TaskTagReportTask class. After the task has been defined then you are able to run the tasktagreport task.

## Parameters/Attributes
outFile
: the path + file name to output the results to.

dir
: the directory base that includes our files we want to parse.

format
: format that the results will be parsed into. (CSV, HTML, TEXT, XML) (HTML implemented by rts000is)

pattern
: this attribute is used in union with the replace attribute this is a regular expression

replace
: this attribute is used in union with the pattern attribute and when the pattern is matched this replacement text will be used. (default is empty string).

applicationName
: The name of the application that is being built.

repository
: The repository code was pulled from

branchOrTagName
: The branch name code was pulled from

buildName
: The build name of the application

### Parameters specified as nested elements &lt;include name="" /&gt; and &lt;exclude name="" /&gt;
Each such element defines a single pattern for files to include or exclude.
