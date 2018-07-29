package fr.iccorp.shana.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Section.class)
public abstract class Section_ {

	public static volatile SingularAttribute<Section, String> titre;
	public static volatile SingularAttribute<Section, String> textAvant;
	public static volatile SingularAttribute<Section, byte[]> photo;
	public static volatile SingularAttribute<Section, Long> id;
	public static volatile SingularAttribute<Section, String> textApres;
	public static volatile SingularAttribute<Section, String> photoContentType;
	public static volatile SingularAttribute<Section, Article> article;

}

