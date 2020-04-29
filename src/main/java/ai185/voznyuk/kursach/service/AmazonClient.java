package ai185.voznyuk.kursach.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AmazonClient {
    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    public boolean upload(File file, String filename, String bucketName){
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, filename, file).withCannedAcl(CannedAccessControlList.PublicRead));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<String> getAllFileNameInBucket(String bucketName){
        List<String> bucketLst = new ArrayList<>();
        ObjectListing objectListing = amazonS3Client.listObjects(bucketName);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        for(S3ObjectSummary objectSummary : objectSummaries){
            bucketLst.add(objectSummary.getKey());
        }
        return bucketLst;
    }

    public ByteArrayOutputStream downloadFile(String fileName, String bucketName){
        try {

            S3Object s3object = amazonS3Client.getObject(new GetObjectRequest(bucketName, fileName));

            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void deleteFromBucket(String bucketName, String fileName){
        try {
            amazonS3Client.deleteObject(bucketName, fileName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getBucketName() {
        return bucketName;
    }
    public String generateFileName(String name) {
        return new Date().getTime() + "-" + name.replace(" ", "_");
    }
}