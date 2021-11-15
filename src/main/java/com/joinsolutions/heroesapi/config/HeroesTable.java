package com.joinsolutions.heroesapi.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Configuration;

import static com.joinsolutions.heroesapi.constant.HeroesConstant.*;

import java.util.Arrays;

@Configuration
@EnableDynamoDBRepositories
public class HeroesTable {

	public static void main(String[] args) {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(DYNAMO_ENDPOINT, DYNAMO_REGION))
				.build();

		DynamoDB dynamoDB = new DynamoDB(client);
		String tableName = "Tb_Heroes";

		try {
			Table table = dynamoDB.createTable(tableName, Arrays.asList(new KeySchemaElement("id", KeyType.HASH)),
					Arrays.asList(new AttributeDefinition("id", ScalarAttributeType.S)),
					new ProvisionedThroughput(5L, 5L));

			table.waitForActive();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
