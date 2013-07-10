package cjd;

import java.io.File;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.logging.SystemStreamLog;

public class MojoTest extends AbstractMojoTestCase {

    private Mojo mojo;
    private String infoOut = "";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        File pom = new File(MojoTest.class.getResource("/test_pom.xml").getFile());
        mojo = lookupMojo("run", pom);

        mojo.setLog(new SystemStreamLog() {
            public void info(CharSequence cs) {
                super.info(cs);
                infoOut += cs;
            }
        });
    }

    public void testThatItStarted() throws Exception
    {
        mojo.execute();
        assertTrue(infoOut.indexOf("Hello World") != -1);
    }
}
