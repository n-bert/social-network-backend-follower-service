package kata.academy.eurekafollowerservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    @Entity
    @Table(name = "followers", uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "followerId"})})
    public class Follower {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        private Long userId;

        private Long followerId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Follower follower = (Follower) o;
            return Objects.equals(id, follower.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
   }


