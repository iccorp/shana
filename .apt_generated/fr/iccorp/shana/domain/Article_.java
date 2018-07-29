package fr.iccorp.shana.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Article.class)
public abstract class Article_ {

	public static volatile SingularAttribute<Article, ZonedDateTime> dateCreation;
	public static volatile SingularAttribute<Article, Categorie> categorie;
	public static volatile SingularAttribute<Article, String> titre;
	public static volatile SingularAttribute<Article, Integer> nbPartage;
	public static volatile SingularAttribute<Article, byte[]> photo;
	public static volatile SingularAttribute<Article, Integer> nbVue;
	public static volatile SingularAttribute<Article, String> nom;
	public static volatile SetAttribute<Article, Section> sections;
	public static volatile SingularAttribute<Article, ZonedDateTime> dateDerniereModification;
	public static volatile SingularAttribute<Article, Integer> nbLike;
	public static volatile SingularAttribute<Article, Integer> positionDansCategorie;
	public static volatile SingularAttribute<Article, Long> id;
	public static volatile SingularAttribute<Article, Integer> position;
	public static volatile SingularAttribute<Article, String> photoContentType;

}

