package com.neiljbrown.awscdkworkshop;

import com.github.eladb.dynamotableviewer.TableViewer;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;

/**
 * An Amazon {@link Stack CDK Stack} (code representation of a single CloudFormation Stack) that declares the bunch of
 * AWS resources that need to be provisioned for this app, using CloudFormation.
 */
public class AmazonCdkWorkshopStack extends Stack {

    private static final String HIT_COUNT_TABLE_HITS_COL_NAME = "hits";

    // Prefix TableViewer construct uses to denote sort order of descending (highest to lowest)
    private static final String TABLEVIEWER_SORT_ORDER_PREFIX_DESC = "-";

    /**
     * Creates an instance of this Stack for the specified parent (app) and with the specified construct ID, using the
     * default Stack properties.
     *
     * @param parent parent of this stack, usually an `App` or a `Stage`, but could be any construct.
     * @param id the construct ID of this stack.
     * @see AmazonCdkWorkshopStack#AmazonCdkWorkshopStack(Construct, String, StackProps)
     */
    public AmazonCdkWorkshopStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    /**
     * Creates  an instance of this Stack for the specified parent (app), with the specified construct ID, and using the
     * specified {@link StackProps Stack Properties}
     *
     * @param parent parent of this stack, usually an `App` or a `Stage`, but could be any construct.
     * @param id the construct ID of this stack.
     * @param props any properties for this stack.
     * @see Stack#Stack(software.constructs.Construct, String, StackProps)
     */
    public AmazonCdkWorkshopStack(final Construct parent, final String id, final StackProps props) {
        // Initialise this Stack in the standard way
        super(parent, id, props);

        // Add the Lambda function to this app's stack that handles requests to the app's API endpoint
        final Function helloLambdaFunction = Function.Builder.create(this, "HelloHandler")
                // Lambda runtime used by the function
                .runtime(Runtime.NODEJS_14_X)
                // Path of folder containing function code (relative to where cdk executed from, project’s root dir)
                .code(Code.fromAsset("lambda"))
                // Lambda function contained in file "hello" and named "handler"
                .handler("hello.handler")
                .build();

        // Provision the set of AWS resources required by  a 'HitCounter' using a custom construct
        final HitCounter helloFunctionWithHitCounter = new HitCounter(this, "HitCounter",
            HitCounterProps.builder().downstream(helloLambdaFunction).build()
        );

        // Declare an API Gateway REST API that proxies the 'hello' Lambda function
        final LambdaRestApi lambdaRestApi = LambdaRestApi.Builder.create(this, "Endpoint")
                // Route the request to the HitCounter function that proxies the Hello function
                .handler(helloFunctionWithHitCounter.getHandler())
                .build();

        // Provision the set of AWS resources required to support a (DynamoDB) 'TableViewer', using a third party CDK
        // construct
        TableViewer.Builder.create(this, "ViewerHitCount")
                .title("Hello Hits")
                .table(helloFunctionWithHitCounter.getTable())
                .sortBy(TABLEVIEWER_SORT_ORDER_PREFIX_DESC + HIT_COUNT_TABLE_HITS_COL_NAME)
                .build();
    }
}
