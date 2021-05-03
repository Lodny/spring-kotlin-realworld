package com.lodny.springkotlinrealworld.tag

import com.fasterxml.jackson.annotation.JsonRootName
import javax.persistence.*

@Entity
@JsonRootName("tag")
data class Tag(@Column(unique = true, nullable = false) val name: String,
               var count: Int = 0,
               @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0) {
}