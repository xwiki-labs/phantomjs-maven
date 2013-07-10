package cjd;

import java.io.IOException;
import java.io.File;
import java.util.Arrays;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.AbstractMojo;

import org.openqa.selenium.os.CommandLine;
import org.jboss.arquillian.phantom.resolver.PhantomJSBinaryResourceResolver;
import org.jboss.arquillian.phantom.resolver.PhantomJSBinary;

/**
 * Read the Code :)
 * @goal run
 */
public class Mojo extends AbstractMojo
{
    /** @parameter required=true */
    private String[] args = {};

    /** @parameter */
    private String cwd;

    private String phantomJsPath()
    {
        String path = CommandLine.find("phantomjs");
        if (path != null) {
            return path;
        }

        getLog().info("Getting phantomjs from Maven");
        try {
            File temp = File.createTempFile("phantomjs-binary-", "");
            File b = new PhantomJSBinaryResourceResolver().resolve(temp).getLocation();
            b.deleteOnExit();
            return b.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute() throws MojoExecutionException
    {
        String[] allArgs = new String[args.length + 1];
        for (int i = 0; i < args.length; i++) {
            allArgs[i+1] = args[i];
        }
        allArgs[0] = phantomJsPath();
        getLog().info("Starting " + phantomJsPath() + " " + Arrays.asList(args));
        CommandLine cmd = new CommandLine(allArgs);
        if (cwd != null) {
            cmd.setWorkingDirectory(cwd);
        }
        cmd.execute();
        getLog().info(cmd.getStdOut());
        if (cmd.getExitCode() != 0) {
            throw new MojoExecutionException("phantomjs returned [" + cmd.getExitCode() + "]");
        }
    }
}
