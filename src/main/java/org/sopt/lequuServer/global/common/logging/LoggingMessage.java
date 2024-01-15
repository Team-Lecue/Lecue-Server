package org.sopt.lequuServer.global.common.logging;

import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.note.model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingMessage {

    public static String memberRegisterLogMessage(Member member) {
        return "🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣🐣\n\n" +
                "- 🐣 새로운 유저가 회원가입을 완료했습니다!\n" +
                "- 🏆 누적 회원가입 수: " + member.getId() + " 명\n" +
                "\n" +
                "- 👀 카카오 닉네임: " + member.getSocialNickname() + "\n" +
                "- 📩 카카오 ID: " + member.getSocialId() + "\n" +
                "- 📷 카카오 프로필 사진: " + member.getSocialProfileImage();
    }

    public static String bookCreateLogMessage(Member member, Book book) {
        return "📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝\n\n" +
                "- 📝 유저가 새로운 레큐북을 생성했습니다!\n" +
                "- 👀 유저 닉네임: " + member.getNickname() + "\n" +
                "- ⏰ 생성 시간: " + book.getCreatedAt() + "\n" +
                "\n" +
                "- 📝 레큐북 제목: " + book.getTitle() + "\n" +
                "- 💬 레큐북 소개: " + book.getDescription() + "\n" +
                "- 🔗 레큐북 링크: https://www.lecue.me/lecue-book/" + book.getUuid() + "\n" +
                "- ✨ 최애 이름: " + book.getFavoriteName() + "\n" +
                "- 📷 최애 이미지: " + book.getFavoriteImage();
    }

    public static String noteCreateLogMessage(Member member, Book book, Note note) {
        return "💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌\n\n" +
                "- 💌 유저가 새로운 레큐노트를 남겼습니다!\n" +
                "- 👀 유저 닉네임: " + member.getNickname() + "\n" +
                "- ⏰ 남긴 시간: " + note.getCreatedAt() + "\n" +
                "\n" +
                "- 🔗 레큐북 링크: https://www.lecue.me/lecue-book/" + book.getUuid() + "\n" +
                "- 📝 레큐북 제목: " + book.getTitle() + "\n" +
                "- 💬 레큐노트 내용: " + note.getContent() + "\n";
    }

    public static String serverErrorMessage() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(new Date());
        return "🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨\n\n" +
                "- ⚠️ ERROR TIME: " + formattedDate + "\n" +
                "- 🚨 야생의 서버 에러가 발생했다!: ";
    }

    public static String jwtErrorMessage() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(new Date());
        return "🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨🚨\n\n" +
                "- ⚠️ ERROR TIME: " + formattedDate + "\n" +
                "- 🚨 JWT 에러 필터에서 오류 발생!: ";
    }
}
