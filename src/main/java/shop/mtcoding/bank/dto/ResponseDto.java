package shop.mtcoding.bank.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseDto<T> {
    private final Integer code; // 1성공 -1실패
    private final String msg;
    private final T data;
}
