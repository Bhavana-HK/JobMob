package com.cvmaker.resources;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtility {
private static final SessionFactory sessionFactory;
static {
  try {
   Configuration cfg = new Configuration().configure("com/cvmaker/resources/hibernate.cfg.xml");
   ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
     .buildServiceRegistry();
   sessionFactory = cfg.buildSessionFactory(serviceRegistry);
  } catch (Throwable ex) {
   System.err.println("Initial SessionFactory creation failed. " + ex);
   throw new ExceptionInInitializerError(ex);
  }
}

 public static SessionFactory getSessionFactory() {
  return sessionFactory;
}
}