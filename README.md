# AWS CDK Workshop

This project contains a Java implementation of an AWS Cloud Development Kit (CDK) app that was developed as 
part of  completing the AWS CDK online tutorial ["CDK workshop"](https://cdkworkshop.com/).

The app maintains a persistent record of the number of requests ('hits') made to an API endpoint. It is implemented 
as an AWS serverless app using AWS API Gateway, a couple of Lambda functions, and DynamoDB. The supported AWS 
resources, and the app code, are deployed using the AWS CDK. 

![alt text](application-overview.png "Application Overview")

The app includes a custom CDK construct ('HitCounter') that provides an example of how to bundle a bunch of AWS 
resources / infrastructure into a higher-level reusable components that anyone can compose into their apps. In this example the CDK construct is used to provision the application's backend (Lambda function and Dynamo DB table). 

The app also contains an example of how to consume a CDK construct published by a third party. In this example, the 
construct is consumed from a public Maven repo and provides a web page for viewing a DynamoDB table.

## 1) Overview of Project
This is a Maven project. It was originally seeded from a Java / Maven CDK app template provided by the CDK, using the 
following command -
```
cdk init sample-app --language java
```

The table below contains a list of the major files in the project and their purpose - 

|Name|Description|
|----|-----------|
|AmazonCdkWorkshopApp.java|Java class that provides the entrypoint for the CDK app. It loads the CDK stack defined in AmazonCdkWorkshopStack.java.|
|AmazonCdkWorkshopStack.java|Java class in which the CDK application’s main stack is defined.|
|AmazonCdkWorkshopStackTest.java| An automated JUnit test that verifies that the CDK app’s synthesising method produces a CloudFormation (JSON) stack template containing the expected AWS resources.|
|HitCounter.java|A custom CDK Construct that can be attached to a Lambda function that’s used as a backend for an API Gateway, which counts the number of requests received for each URL path, and stores it in a DynamoDB table.|
|pom.xml|Maven build script. Contains information on build properties, dependencies, and app information.|
|cdk.json|Tells the CDK toolkit (CLI) how to run the app. In the case of a Java CDK app that uses Maven it uses the Maven command "mvn -q exec:java".|

## 2) Prerequisites for Building and Deploying Project
To build and deploy this project / app you'll need the following installed in your local dev environment. Further 
instructions  for each can be found in the AWS Cloud Development Kit Developer Guide, section Getting Started > 
[Prerequisites](https://docs.aws.amazon.com/cdk/v1/guide/getting_started.html#getting_started_prerequisites).  

### AWS Account and User
You’ll need access to an AWS account and an AWS IAM user (or role) in that account with API access keys with the 
permissions to provision the required resources.

The project configures the AWS CDK to use an AWS user profile named 'cdk-workshop' by default. This can be 
overridden by editing cdk.json. 

### Node.js
The AWS CDK depends on Node.js (irrespective of the language used to implement the CDK app, in this case Java). 
Ensure you have Node.js version >=10.3.0. Otherwise, install the latest version from  the [Node.js website](https://nodejs.org).

### AWS CDK Toolkit
The AWS CDK Toolkit is a command-line utility (CLI) that allows you to work with CDK apps. It's installed using the 
Node Package Manager (NPM), using the following command - `npm install -g aws-cdk`

### Java and Maven
To compile the project, run tests and execute the cdk CLI commands you'll need to have a JDK version 8 or 
higher, and Maven 3.6.3 or higher. The simplest way to  install both is to use [SDKMAN](https://sdkman.io/).

## 3) Useful commands
* `mvn package` - Compile and run the tests.
* `cdk ls` - List all stacks in the app.
* `cdk synth` - Emit the synthesized CloudFormation template.
* `cdk deploy` - Deploy this stack to your default AWS account/region.
* `cdk diff` - Compare the deployed stack with current state.
* `cdk docs` - Open CDK documentation in your browser.

End.