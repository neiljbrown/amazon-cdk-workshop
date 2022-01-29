package com.neiljbrown.awscdkworkshop;

import java.util.Map;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;

/**
 * A custom CDK {@link Construct} that can be attached to a Lambda function that’s used as a backend for an API Gateway,
 * which counts the number of requests received for each URL path, and stores it in a DynamoDB table.
 */
public class HitCounter extends Construct {
	private final Function handler;
	private final Table table;

	/**
	 * @param scope parent of this stack, usually an `App` or a `Stage`, but could be any construct.
	 * @param id the construct ID of this stack.
	 * @param props any properties for this stack.
	 */
	public HitCounter(Construct scope, String id, HitCounterProps props) {
		super(scope, id);

		// Provision the DynamoDB table for maintaining the count of the no. of requests per path
		this.table = Table.Builder.create(this, "Hits")
			// Include a partition key, which is mandatory for every DynamoDB table
			.partitionKey(Attribute.builder().name("path").type(AttributeType.STRING).build())
			.build();

		// Provision/deploy the Lambda function that is used to maintain the hit count when each API request is received

		// Create the env variables used by the Lambda function
		final Map<String, String> environment = Map.of(
			"HITS_TABLE_NAME", this.table.getTableName(),
			"DOWNSTREAM_FUNCTION_NAME", props.getDownstream().getFunctionName()
		);
		this.handler = Function.Builder.create(this, "HitCounterHandler")
			.runtime(Runtime.NODEJS_10_X)
			.handler("hitcounter.handler")
			.code(Code.fromAsset("lambda"))
			.environment(environment)
			.build();

		this.table.grantReadWriteData(this.handler);
		props.getDownstream().grantInvoke(this.handler);
	}

	/**
	 * @return the {@link Function} for the Lambda function that this CDK Construct provisions/deploys to maintain the
	 * hit count when each API request is received
	 */
	Function getHandler() {
		return this.handler;
	}

	/**
	 * @return the {@link Table} for the DynamoDB table that this CDK Construct uses to maintain the count of the no.
	 * of requests per path.
	 */
	Table getTable() {
		return this.table;
	}
}