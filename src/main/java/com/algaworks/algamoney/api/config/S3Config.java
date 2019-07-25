package com.algaworks.algamoney.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Tag;
import com.amazonaws.services.s3.model.lifecycle.LifecycleFilter;
import com.amazonaws.services.s3.model.lifecycle.LifecycleTagPredicate;

@Configuration
public class S3Config {

	private static final String BUCKET_NAME = "ah-algamoney-arquivos";
	
//	TODO: CONFIGURAR ACCESS_KEY
	private static final String ACCESS_KEY = System.getenv("S3_ACCESS_KEY");
	
//	TODO: CONFIGURAR SECRET_KEY
	private static final String SECRET_KEY = System.getenv("S3_SECRET_KEY");
	
	@Bean
	public AmazonS3 amazons3() {

		AWSCredentials credenciais = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		
		AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
												 .withCredentials(new AWSStaticCredentialsProvider(credenciais))
												 .withRegion(Regions.US_EAST_2)
												 .build();
		
		if (!amazonS3.doesBucketExistV2(BUCKET_NAME)) {
			amazonS3.createBucket(new CreateBucketRequest(BUCKET_NAME));
			
			BucketLifecycleConfiguration.Rule regraExpiracao = new BucketLifecycleConfiguration.Rule()
					.withId("Regra de expiração de arquivos temporários")
					.withFilter(new LifecycleFilter(new LifecycleTagPredicate(new Tag("expirar", "true"))))
					.withExpirationInDays(1)
					.withStatus(BucketLifecycleConfiguration.ENABLED);
			
			BucketLifecycleConfiguration configuration = new BucketLifecycleConfiguration().withRules(regraExpiracao);
			
			amazonS3.setBucketLifecycleConfiguration(BUCKET_NAME, configuration);
		}
		
		return amazonS3;
	}
}
