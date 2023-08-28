## 개발 추가사항

### 23년 08월 25일 추가사항
- [x] logger 적용
- [x] jasypt 적용
- [x] 글로벌 exception 처리 적용

### 23년 08월 24일 추가사항
- [x] 로그인 엑세스 토큰 구축
- [x] 세션 유지시간 30분 구축 및 리프레시 토큰
- [ ] logger 적용
- [ ] 글로벌 exception 처리 적용

## 상세 내용
추후 블로그 링크로 전환할 예정. 현재는 빠르게 작업하기 위해서 readme에 작성중.

### Login

### Session

### Logback

### Filter

### Jastpy
PooledPBEStringEncryptor 생성과 설정:

PooledPBEStringEncryptor는 Jasypt에서 제공하는 암호화 클래스로, 여러 요청에 대한 암호화를 효율적으로 처리할 수 있도록 풀링된 리소스를 사용합니다.

encryptor.setAlgorithm("PBEWithMD5AndDES");: 사용할 암호화 알고리즘을 설정합니다.
encryptor.setPassword("your-secret-key");: 암호화 및 복호화에 사용될 비밀 키를 설정합니다.
encryptor.setPoolSize(5);: 암호화 풀의 크기를 설정합니다.

encryptor.setStringOutputType("BASE64");:

암호화된 문자열의 출력 형식을 설정합니다. BASE64, HEXADECIMAL, NO_ENCODING 중 하나를 선택할 수 있습니다.

Salt Generator 설정:

솔트(salt)는 암호화를 더욱 안전하게 만들기 위해 사용되는 임의의 값을 의미합니다. RandomSaltGenerator는 무작위 솔트를 생성하는 클래스입니다. 설정하지 않을 경우 기본 값으로 ZeroSaltGenerator가 사용됩니다.

IV Generator 설정:

IV(Initialization Vector)는 암호화된 데이터의 초기화 벡터를 의미합니다. RandomIvGenerator를 사용하여 무작위 IV를 생성합니다.

Key Obfuscation 설정:

encryptor.setKeyObtentionIterations(1000);: 비밀 키를 추출하기 위해 반복 횟수를 설정합니다. 이 값이 높을수록 추출 시간이 길어지며 보안이 강화됩니다.