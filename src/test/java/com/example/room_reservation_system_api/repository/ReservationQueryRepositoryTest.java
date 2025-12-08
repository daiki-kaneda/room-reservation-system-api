package com.example.room_reservation_system_api.repository;

import com.example.room_reservation_system_api.entity.Reservation;
import com.example.room_reservation_system_api.entity.Room;
import com.example.room_reservation_system_api.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // JPAコンポーネントだけをロードしてH2でテストする
class ReservationQueryRepositoryTest {

    @Autowired
    private ReservationQueryRepository queryRepository;

    @Autowired
    private TestEntityManager entityManager; // テスト用データ投入ヘルパー

    private Room testRoom;
    private User testUser;

    @BeforeEach
    void setUp() {
        // テストデータの準備
        // RoomとUserを作って永続化しておく
        testRoom = Room.create("Test Room"); // コンストラクタやセッターに合わせて調整してください
        testUser = User.create("uid1", "Test User", "test@example.com");
        
        entityManager.persist(testRoom);
        entityManager.persist(testUser);
        
        // 既存の予約を入れる: 10:00 〜 11:00
        Reservation existing = Reservation.create(
            testUser, 
            testRoom, 
            LocalDateTime.of(2025, 1, 1, 10, 0), 
            LocalDateTime.of(2025, 1, 1, 11, 0)
        );
        entityManager.persist(existing);
        entityManager.flush();
    }

    @Test
    @DisplayName("重複あり: 時間が完全に一致する場合")
    void testExactMatch() {
        boolean result = queryRepository.existsOverlappingReservation(
                testRoom.getId(),
                LocalDateTime.of(2025, 1, 1, 10, 0),
                LocalDateTime.of(2025, 1, 1, 11, 0)
        );
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("重複あり: 既存予約の一部に被る場合(後半被り)")
    void testOverlapTail() {
        // 10:30 〜 11:30 (10:30〜11:00が被る)
        boolean result = queryRepository.existsOverlappingReservation(
                testRoom.getId(),
                LocalDateTime.of(2025, 1, 1, 10, 30),
                LocalDateTime.of(2025, 1, 1, 11, 30)
        );
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("重複なし: 既存予約の直後(境界値)")
    void testNoOverlapJustAfter() {
        // 11:00 〜 12:00 (11:00終了、11:00開始はOKであるべき)
        boolean result = queryRepository.existsOverlappingReservation(
                testRoom.getId(),
                LocalDateTime.of(2025, 1, 1, 11, 0),
                LocalDateTime.of(2025, 1, 1, 12, 0)
        );
        assertThat(result).isFalse();
    }
    
    @Test
    @DisplayName("重複なし: 全く別の部屋の場合")
    void testNoOverlapDifferentRoom() {
        // 別の部屋を作る
        Room otherRoom = Room.create("Other Room");
        entityManager.persist(otherRoom);

        // 時間は被っているが、部屋が違う
        boolean result = queryRepository.existsOverlappingReservation(
                otherRoom.getId(),
                LocalDateTime.of(2025, 1, 1, 10, 0),
                LocalDateTime.of(2025, 1, 1, 11, 0)
        );
        assertThat(result).isFalse();
    }
}
