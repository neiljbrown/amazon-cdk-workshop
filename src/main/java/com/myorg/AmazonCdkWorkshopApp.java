package com.myorg;

import software.amazon.awscdk.core.App;

/**
 * An AWS Cloud Development Kit (CDK) app.
 */
public final class AmazonCdkWorkshopApp {

  /**
   * Entry point for executing the app from the command line. Used by CDK CLI commands like 'cdk deploy' to generate
   * CloudFormation templates.
   *
   * @param args command line arguments.
   */
  public static void main(final String[] args) {
    // Create and initialise a new instance of an Amazon CDK app.
    App app = new App();

    // Create an instance of the CDK's representation of CloudFormation stack, declaring the AWS resources we wish to
    // provision using CloudFormation, and make it part of this CDK app
    new AmazonCdkWorkshopStack(app, "AmazonCdkWorkshopStack");

    // Synthesise the CDK app and all of its stacks - generate CloudFormation templates, writing them to folder cdk.out
    app.synth();
  }
}