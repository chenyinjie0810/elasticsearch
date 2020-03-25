package com.zfsoft.elasticsearch.constans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chenjian
 */
@Component
public class KafkaConstans {

    /**
     * 运行平台公匙
     */
    @Value("${yxpt.public.key.string}")
    public String YXPT_PUBLIC_KEY_STRING = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCY9T2bu//LyaHgwjUw9xUIcGgRIuMhK8B+2fuxeg5OybBaFbyf3PjhH3Zf5NmY761cAnO6/Tmtb/gR0WlacZ+QBlQ61VZ0zvW0bijmlGg0j9FStVYiSIzv2U7u2TpD0za9m9RSpPqZk7Cle7RqwWxHr2V+S+He5aaqMSWaxv4uXwIDAQAB";

    /**
     * 运行平台私匙
     */
    @Value("${yxpt.private.key.string}")
    public String YXPT_PRIVATE_KEY_STRING = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJj1PZu7/8vJoeDCNTD3FQhwaBEi4yErwH7Z+7F6Dk7JsFoVvJ/c+OEfdl/k2ZjvrVwCc7r9Oa1v+BHRaVpxn5AGVDrVVnTO9bRuKOaUaDSP0VK1ViJIjO/ZTu7ZOkPTNr2b1FKk+pmTsKV7tGrBbEevZX5L4d7lpqoxJZrG/i5fAgMBAAECgYB5MrFLag7Ikg8Tga8WCmH/GtpK4cLo8LsJiH3X5efaZBO5fcnFPhlygDSSzUnh1eAerM641pdSJwuAStdY0/lirdJZyDaLjMOW/Y3aXByfqIhvRac4HsN0nI4DSov7ekrDf2SAwyG/boKRMU4KlsXpJXrOFZoU4QkL+dkOj6F/uQJBAO2rGc8kqoivzi266VsPlX3o698zBF/8jO30lPxZUX/8l7ZO7HMVVjciYX51DeGQcy6sWDzjv57xScgi9NN+0u0CQQCkwXv0529cxaHH5stmC3nW2mqzdV8t39BJfpDvlL8VVQvNZQT7G6uvU8PYLduJc5WzODEkyVnkG5dX8/rkruD7AkB0boAEVKgeslHFYW30qFvKBROYkruv8l9wK4PZZEBi/PGt5Fg9wNQtynAMrXeRa1yiHthTbBdx3C8TFtztx5G5AkBTAplt312gpILKsWIx2/5bXlj8alq2RlovbXGhBJTyLpNvvUIxMtPh1paKSTCfpHj8f4djPX/pCm3KhscWiXYZAkEAzAbmZP1JTDhIqwFCFxKuLBkSVGC1TBKLKvd4tBZ/5MizsPxKYRiuSx4EaHCAew4P/fnVvuAwlUX1J5DBuwM/NQ==";

    @Value("${datacenter.id}")
    public Long DATACENTER_ID;

    @Value("${machine.id}")
    public Long MACHINE_ID;
}
