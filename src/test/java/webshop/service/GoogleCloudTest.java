package webshop.service;

import com.google.auth.oauth2.GoogleCredentials;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.Assert.assertNotNull;

@Transactional
public class GoogleCloudTest {
        @Test
        public void testGoogleApplicationCredentials() {
            // GOOGLE_APPLICATION_CREDENTIALS 환경 변수 값 가져오기
            String credentialsFilePath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

            if (credentialsFilePath != null && !credentialsFilePath.isEmpty()) {
                // GOOGLE_APPLICATION_CREDENTIALS 환경 변수가 설정되어 있음
                System.out.println("GOOGLE_APPLICATION_CREDENTIALS is set to: " + credentialsFilePath);
                try {
                    // 인증 정보 로드
                    GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
                    assertNotNull("Google Application Credentials loaded successfully.", credentials);
                } catch (Exception e) {
                    fail("Failed to load Google Application Credentials: " + e.getMessage());
                }
            } else {
                // GOOGLE_APPLICATION_CREDENTIALS 환경 변수가 설정되어 있지 않음
                fail("GOOGLE_APPLICATION_CREDENTIALS is not set. Please set this environment variable to the path of your service account key file.");
            }
        }
}
