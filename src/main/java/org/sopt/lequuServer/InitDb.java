package org.sopt.lequuServer;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.model.SocialPlatform;
import org.sopt.lequuServer.domain.sticker.model.Sticker;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.sopt.lequuServer.domain.sticker.model.StickerCategory.*;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    private static final List<String> CHARACTER_STICKERS = Arrays.asList(
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/sticker_foryou.svg",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/sticker_love.svg",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012477@3x.png",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012478@3x.png",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012484@3x.png",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012485@3x.png",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012486@3x.png",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012489@3x.png",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012491@3x.png"
    );

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        @Transactional
        public void dbInit() {

            if (!isDatabaseEmpty()) {
                return;
            }

            Member member1 = Member.builder()
                    .socialPlatform(SocialPlatform.KAKAO)
                    .socialId("3251153440")
                    .build();
            member1.updateSocialInfo("레큐", "http://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg");
            member1.updateNickname("양파큐야");
            em.persist(member1);

            /**
             * 레큐북 생성 부분
             */

            for (BookDummy bookDummy : BookDummy.values()) {
                Book book = Book.builder()
                        .uuid(bookDummy.getUuid())
                        .favoriteName(bookDummy.getFavoriteName())
                        .favoriteImage(bookDummy.getFavoriteImage())
                        .title(bookDummy.getTitle())
                        .description(bookDummy.getDescription())
                        .backgroundColor("#F5F5F5")
                        .member(member1)
                        .popularRate(1)
                        .build();
                em.persist(book);
            }

            /**
             * 스티커 생성 부분
             */

            for (int i = 4; i < 10; i++) {
                Sticker sticker = Sticker.builder()
                        .bookId(0L)
                        .category(CAT)
                        .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_100001249" + i + "@3x.png")
                        .build();
                em.persist(sticker);
            }
            for (int i = 1; i < 7; i++) {
                Sticker sticker = Sticker.builder()
                        .bookId(0L)
                        .category(EVENT)
                        .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/" + i + "-" + i + ".svg")
                        .build();
                em.persist(sticker);
            }
            for (int i = 1; i < 15; i++) {
                Sticker sticker = Sticker.builder()
                        .bookId(0L)
                        .category(CUSTOM)
                        .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/" + i + ".svg")
                        .build();
                if (i == 6) {
                    sticker = Sticker.builder()
                            .bookId(0L)
                            .category(CUSTOM)
                            .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/" + i + ".png")
                            .build();
                }
                em.persist(sticker);
            }
            Sticker sticker2 = Sticker.builder()
                    .bookId(0L)
                    .category(CUSTOM)
                    .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012465@3x.png")
                    .build();
            em.persist(sticker2);

            for (String url : CHARACTER_STICKERS) {
                Sticker sticker = Sticker.builder()
                        .bookId(0L)
                        .category(ITEM)
                        .stickerImage(url)
                        .build();
                em.persist(sticker);
            }
        }

        private boolean isDatabaseEmpty() {
            Long count = em.createQuery("SELECT COUNT(m) FROM Member m", Long.class)
                    .getSingleResult();
            return count == 0;
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum BookDummy {
        LESSERAFIM("31670e51-5e23-4f66-a419-4f706bde7811", "르세라핌", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/19ad6f3d-fc8b-45e1-a824-35ab6b219d01.jpg", "너 내 도도독…", "나는 르세라핌 100살까지 사랑할게용❤️"),
        SEVENTEEN("272be092-cbf4-42d6-804d-581023bd584e", "세븐틴", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/76717563-9133-42b5-99d5-e5299f083003.jpg", "세상을 흔들자, 붐붐!✋✋", "아주 NICE 한 세븐틴에게 한마디 ~"),
        DAYSIX("2fbe12f8-3c0f-4291-a9fa-21bd6787f1e4", "데이식스", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/a08fbdbe-d203-4614-9563-5c00695c594e.jpg", "우주최강아이돌밴드 데이식스", "4년만에 완전체로 돌아온 데이식스에게 한마디 ~"),
        LEECHANHYUK("ae2b3f93-5745-4f60-ac59-37640e6b2d4a", "이찬혁", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/0e474a56-5de1-4d0f-b2f8-c61cf60ba288.jpg", "수현이 엄마 아들", "찬혁이 하고 싶은거 레큐에서 다 해🤗"),
        AESPA("f358ce58-cca9-4e37-a5e5-37764e7a0ab0", "에스파", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/abc542e3-8bfd-4e79-bcb2-06d3f4963464.jpg", "광야 속 에스파👠", "에스파는 나야 둘이 될 수 없어"),
        PSICK("3ad6005b-34d8-45b0-bfae-6df5f6f54ea5", "Psick UNIV.", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/2e3de333-fc0d-45a9-b8fa-f5d122fb7546.jpg", "피식대학 폼 미쳤다이~😎", "클린 앤 깔끔-"),
        HORSEKING("5072d5e6-30d4-4ee9-a8dc-ef8a7d2cc998", "말왕", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/89e86133-fc52-4b3a-ad13-179623823b3e.jpg", "장충동 왕 족발 보쌈~♬", "이거보세요오!!!! 이렇게?!? 이렇게!! 이렇게??🍖"),
        QWER("388d50b6-2c1e-4b62-9f69-e6c0c8371f03", "QWER", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/9a7d5415-46cf-4533-a4db-c49983540959.jpg", "지구를 정복하는 그날까지!", "무대 위 춤을 추는 D선상의 아리아💜"),
        JJANGU("d9de5c2c-2779-41ce-9b23-1635177426de", "짱구", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/8e6b1fa2-8c39-4f8d-aac9-e93a0addf790.jpg", "짱구야~~노올자~~🙋‍️", "천방지축 어리둥절 빙글빙글 돌아가는 짱구의 하루~~ \n짱구는 못말려~~"),
        SATORU("bfedf275-9bce-4c3e-b60d-f103313914fa", "고죠 사토루", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/9c27f106-595e-416d-8b34-0851450f9699.jpg", "최강의 주술사 고죠 ㅋㅋ", "무량공처"),
        PUBAO("404a27a3-02a7-4d09-9888-acebab4fc8ca", "푸바오", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/6690b41d-d43d-487c-9ac6-cb4ab48baf5c.jpg", "푸공주🐼", "가지마 푸바오🥺"),
        FAKER("83f01325-1a87-43b2-a8b1-fc12b6585999", "페이커", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/e324a16d-6d5e-477d-87de-8a16aab3d8f9.jpg", "불 사 대 마 왕🔥", "자, 예배 시간입니다🙏"),
        MANAGER("5292f7d9-c28b-4e57-a1f9-2d307fb21c76", "DO SOPT 운영진", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/57e5dbbf-0727-4f35-b368-1d5d306d5bc9.jpg", "수고한 먼진이들", "DO SOPT 33기 이끄느라 너무 수고 많았다🥺\n🤍양정윤 김대덕 서지원 김채현 김서현 이태희 박익범 최윤한 이호재 이승준 김해린🤍"),
        DOSOPT("3f19624f-ca12-4d46-a139-18a4919bfc83", "DO SOPT 33기", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/b0b0b1ed-506b-48cc-ac35-6912d6a6c916.jpg", "DO SOPT 33기", "5개월간 모두 수고 많았어요🤍"),
        LECUE("0c388ecd-e149-4209-938f-f33b21f1d669", "LECUE", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/48b35fb4-fce8-4711-89fa-b8dd5a1084df.jpg", "Team 레큐", "🤍박창준 변현빈 윤영선 송예솔 황예린 이도윤 이정우 서아름 권은빈 현예진 이동섭 김동휘🤍"),
        NONSUL("f4f3a55e-06a6-455b-8ef0-952fde725e02", "논술메이트", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/c6922681-adbc-450f-bc8b-493d2e8a3fae.jpg", "Team 논술메이트", "🤍류가은 고우정 반유진 백주연 박소영 이시연 김민영 권혜인 송민규 김성은🤍"),
        PINGLE("69e524dc-7506-4e8c-a6db-89bec7ff4e3c", "PINGLE", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/9dbfd7c9-ebef-4c10-8fd7-4bd642d03902.jpg", "Team 핑글", "🤍김승연 장지원 박소현 최서인 오승연 김민우 배지현 하지은 이다은 방민지 정채은 강민수 박재연 박상준🤍"),
        MILE("9c48e1e7-23e8-45fb-8149-4ce91fb028c4", "Mile", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/f1ec030e-9795-430a-a2e8-ec12a8791fb2.jpg", "Team 마일", "🤍이동헌 박기현 김지원 강채연 서채원 문다현 윤서진 남다은 이재훈 박희정 도소현🤍"),
        HMH("a27e49ec-90c7-4fce-8519-64e4c57fab89", "하면함", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/14c43cb0-0689-4ad9-88e1-ec12b3237b24.jpg", "Team 하면함", "🤍여민서 김성원 맹유진 유효진 안소희 강유리 곽의진 경지현 김선우 이지희 김보연 김승환 임주민🤍"),
        TOASSTER("cbef9007-ef10-4f4c-b43c-2f16995ec73c", "toaster", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/fe0bbe6f-3c8d-4704-9736-5175046689ff.jpg", "Team 토스터", "🤍이지민 유주현 박소영 이수빈 박시원 이삭 우상욱 박채은 최민영 이민재 김다예 김가현 박준혁 석미혜 김수현🤍"),
        DONTBE("a12908b4-5045-4634-bedd-1e8aedd06039", "Don’t be", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/a4c00ec9-3a48-4646-8a4b-9500da48d0ee.jpg", "Team 돈비", "🤍이승헌 김정언 최소윤 김예인 남희주 원다빈 배찬우 박소현 김언지 김연수 변희주 변상우 김보람 정홍준🤍"),
        MOONSHOT("9b8107dd-e053-4698-a1b9-c5110e727529", "moonshot", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/001fbe49-4ef9-4e79-b04c-30c17c1f56ce.jpg", "Team 문샷", "🤍김민정 유경민 장미현 이지현 박현진 박현진 신수연 전언석 최준민 조연서 최영린 신민철🤍"),
        SOFTIE("76882191-ddee-451a-8108-422da57c1af5", "소프티", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/e3f03110-e592-4f22-90dc-31e1baac1002.jpg", "Team 소프티", "🤍황민정 강민재 김민채 권지민 김민정 박강희 박호연 허민회 김태경 고아라 이우제 김민주 남궁찬 최승빈 김소현🤍"),
        SWEET("939b6a7b-1b3c-4646-9db3-5a4884077384", "Sweet", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/ef4d2919-041f-4da9-a702-737d99f79fdc.jpg", "Team 스윗", "🤍시동훈 전승현 김유진 심채연 강국희 이가영 왕호은 장정안 유지민 송하연 박지영🤍"),
        DOORIP("279f7a79-2013-440f-9dfd-948d5bf37825", "DOORIP", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/78f97ec0-a1d7-40ee-a2bb-f0206c86f99b.jpg", "Team 두립", "🤍이지민 박세은 유아린 황현진 박혜진 박동민 김상호 이유빈 조세연 윤영서 곽성준 윤희슬 윤정원 황선웅🤍"),
        MOTIVOO("bfa82354-ee72-46c1-8b0f-0c250edba4c8", "모티부", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/be3d9b76-482a-4fbb-94b4-65601bf4c5e0.jpg", "Team 모티부", "🤍이가영 정유진 김혜진 김윤지 정가윤 조관희 이준희 엄현지 김준서 이조은 박윤빈 최효리 이혜연 박예준 조찬우🤍"),
        WEBSOSO("d8a28aab-93de-4e19-8ea0-a248f1d3eccf", "웹소소", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/9a30c2f0-70b4-4711-aa97-d88188fb2c20.jpg", "Team 웹소소", "🤍김명진 백송현 은창준 범예은 손명지 서재원 최준서 이연진 김세훈 최서연 신지원 이윤학 전효원 김태욱 이나경 이채은🤍"),
        MODDY("479653bc-508f-4dd8-aad3-d16f854218e1", "moddy", "https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/5154beb4-b0db-470e-bb86-01228c5d26f7.jpg", "Team 소프티", "🤍강재훈 최다민 김태양 백예원 원하연 고가형 황수빈 강민서 조승희 박경린 안현주 강원용🤍");

        private final String uuid;
        private final String favoriteName;
        private final String favoriteImage;
        private final String title;
        private final String description;
    }
}