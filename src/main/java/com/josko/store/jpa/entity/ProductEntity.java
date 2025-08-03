package com.josko.store.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 10, unique = true)
	private String code;

	@Column(nullable = false)
	private String name;

	@Column(name = "price_eur", nullable = false, precision = 10, scale = 2)
	private BigDecimal priceEur;

	@Column(name = "is_available", nullable = false)
	private Boolean isAvailable;
}
