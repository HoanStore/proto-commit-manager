# 커밋 매니저

## 주요 기능 v0.0.1 (일단 핵심 기능 중심으로 빠르게 개발하겠음)
1. 커밋을 모아서 특정 시점에 보낼 수 있는 기능

```
[작업흐름]
1. commit을 patch 파일로 모아 둡니다. 
2. 실제로 이 patch 파일이 저희 서비스에 올라갑니다. 
3. 저희 서비스에 올라온 patch 파일을 바탕으로 매일 한 개씩 커밋이 이루어집니다. 
```

- 패치 파일 만들기
```
git add .
git commit -m "test: README.md"
git format-patch -1 -o patches/
```

- 패치 파일 적용
```
git apply 0001-*.patch 또는 git apply patches/0001-*.patch
```


```
자바로 Git commit 테스트 2025-0327
```


테스트! PAT을 묻는지 확인

아래 명령어를 쳤을 때, 묻는지 확인 
git credential reject https://github.com



- 현재 시간 4월 22일 (화) 오후 04:49

