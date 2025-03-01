package com.peerfect.service.utils;

import com.peerfect.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final MemberRepository memberRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    public String uploadFile(MultipartFile file, String folderName) {
        String fileName = folderName + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            if (response.sdkHttpResponse().isSuccessful()) {
                return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
            } else {
                throw new RuntimeException("S3 업로드 실패");
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 오류 발생", e);
        }
    }

    public int updateMemberImage(String memberId, String imageUrl) {
        return memberRepository.updateMemberImage(memberId, imageUrl);
    }

    public List<String> challengeFileUpload(String memberId, String challengeNo, List<MultipartFile> files) {
        String folderName = "challenges/" + challengeNo + "/" + memberId;
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = folderName + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

            try {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .contentType(file.getContentType())
                        .build();

                PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

                if (response.sdkHttpResponse().isSuccessful()) {
                    String fileUrl = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
                    uploadedUrls.add(fileUrl);
                } else {
                    throw new RuntimeException("S3 업로드 실패");
                }
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 중 오류 발생", e);
            }
        }

        int updatedRows = updateChallengeFiles(challengeNo, memberId, uploadedUrls);
        if (updatedRows == 0) {
            throw new RuntimeException("챌린지 파일 정보 업데이트 실패");
        }

        return uploadedUrls;
    }

    public int updateChallengeFiles(String challengeNo, String memberId, List<String> fileUrls) {
        return MemberRepository.updateChallengeFiles(challengeNo, memberId, fileUrls);
    }

}
