package com.omo.tools.ant.main;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

/**
 * This class handle running test for generating tests for creating the Html report.  This may come in handy when implementing another task or updating a report.
 * 
 * @author Richard Salas JCCC May 05, 2014
 */
public class Application {

    /**
     * This main method will run an ant program (projectTestFile.xml) Programmatically. This is just for testing purposes...as I was implementing
     * the html report.
     * 
     * @author Richard Salas
     * @param args
     *        - arguments that may be passed in to the jvm
     */
    public static void main(final String[] args) {
        final File buildFile = new File("projectTestFile.xml");
        final Project p = new Project();
        p.setUserProperty("ant.file", buildFile.getAbsolutePath()); // this is required as this tells ant where the file is located when being
                                                                    // ran

        // set logger up...
        final DefaultLogger logger = new DefaultLogger();
        logger.setErrorPrintStream(System.err);
        logger.setOutputPrintStream(System.out);
        logger.setMessageOutputLevel(Project.MSG_VERBOSE);
        p.addBuildListener(logger);

        try {
            p.fireBuildStarted(); // build starting message to build listener/logger
            p.init();
            final ProjectHelper helper = ProjectHelper.getProjectHelper();
            p.addReference("ant.projectHelper", helper);
            helper.parse(p, buildFile);
            p.executeTarget(p.getDefaultTarget());
            p.fireBuildFinished(null); // build ending message to build listener/logger
        } catch (final BuildException e) {
            p.fireBuildFinished(e);
        }
    }

}
