#phantomjs-maven

**Motivation**
A simple maven plugin for running PhantomJS with your own arguments.
Based on arquillian-phantom-driver which is a WebDriver backend.
If you want to use WebDriver, use arquillian-phantom-driver, if you
just want to invoke PhantomJS, use this.

**Usage**
Add the following to your maven build plugins.


    <plugin>
        <groupId>cjd</groupId>
        <artifactId>phantomjs-maven</artifactId>
        <version>${phantomjs-maven-version}</version>
        <configuration>
            <cwd>/path/to/desired/working/direcory</cwd>
            <args>
              <arg>your_js_file.js</arg>
              <arg>param1</arg>
              <arg>param2</arg>
            </args>
        </configuration>
    </plugin>

Two Arguments:
1. **cwd** optional parameter, sets the working directory for PhantomJS.
2. **args** required list of arguments to pass to PhantomJS when starting.

If the return code from PhantomJS is non-zero, it will fail the build.
