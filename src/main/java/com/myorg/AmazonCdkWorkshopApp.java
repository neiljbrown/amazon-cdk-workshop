package com.myorg;

import software.amazon.awscdk.core.App;

public final class AmazonCdkWorkshopApp {
    public static void main(final String[] args) {
        App app = new App();

        new AmazonCdkWorkshopStack(app, "AmazonCdkWorkshopStack");

        app.synth();
    }
}
