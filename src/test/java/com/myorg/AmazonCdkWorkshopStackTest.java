package com.myorg;

import software.amazon.awscdk.core.App;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

/**
 * Tests {@link AmazonCdkWorkshopStack}.
 */
public class AmazonCdkWorkshopStackTest {

    private final ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);

    /**
     * Tests an instance of {@link AmazonCdkWorkshopStack} provisions the expected AWS resources required by the app.
     * <p>
     * Does so by verify that the CDK app’s synthesising method produces a CloudFormation (JSON) stack template
     * containing the expected AWS resources.
     *
     * @throws Exception if an unexpected error occurs on execution of this test.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testStack() throws Exception {
        App app = new App();

        AmazonCdkWorkshopStack stack = new AmazonCdkWorkshopStack(app, "test");
        Map<String, Map<String, Object>> cfnTemplateAsMap =
          (Map<String, Map<String, Object>>) app.synth().getStackArtifact(stack.getArtifactId()).getTemplate();

        String cfnTemplateAsJson = this.objectMapper.valueToTree(cfnTemplateAsMap).toString();
        // Cursory check that the synthesised stack contains a selection of the expected types of AWS resources
        assertThat(cfnTemplateAsJson)
          .contains("AWS::IAM::Role")
          .contains("AWS::Lambda::Function")
          .contains("AWS::DynamoDB::Table")
          .contains("AWS::ApiGateway::RestApi");
    }
}