package com.trustrace.tiles_hub_be.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "roles")
public class Role {

        @Id
        private String id;
        private String name;

        public Role(String name) {
            this.name = name;
        }

        //----------------------getters and setters-----------------------
//        public String getId() {
//                return id;
//        }
//
//        public String getName() {
//                return name;
//        }
//
//        public void setName(String name) {
//                this.name = name;
//        }
}
