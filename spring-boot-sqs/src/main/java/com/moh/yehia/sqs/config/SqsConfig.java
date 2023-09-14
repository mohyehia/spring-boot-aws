package com.moh.yehia.sqs.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.moh.yehia.sqs.listener.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

@Configuration
@RequiredArgsConstructor
public class SqsConfig {
    private final SqsProperties sqsProperties;
    private final SqsListener sqsListener;

    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer() {
        SQSConnectionFactory sqsConnectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                amazonSQSAsync()
        );
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setConnectionFactory(sqsConnectionFactory);
        defaultMessageListenerContainer.setMessageListener(sqsListener);
        defaultMessageListenerContainer.setDestinationName("aws-sqs-queue-name");
        return defaultMessageListenerContainer;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        SQSConnectionFactory sqsConnectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                amazonSQSAsync()
        );
        JmsTemplate jmsTemplate = new JmsTemplate(sqsConnectionFactory);
        jmsTemplate.setDefaultDestinationName("aws-sqs-queue-name");
        jmsTemplate.setDeliveryPersistent(false);
        return jmsTemplate;
    }

    @Bean
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(sqsProperties.getServiceEndpoint(), sqsProperties.getSigningRegion())
                ).withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(sqsProperties.getAccessKey(), sqsProperties.getSecretKey())
                        )
                ).build();
    }
}
