# Junit Bank App

### JPA LocalDateTime 자동으로 생성하는 법

- @EnableJpaAuditing (Main 클래스)
- @EntityListerners(AuditingEntityListener.class)
```java
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
```