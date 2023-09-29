package com.MobileSubscribers.MobileSubscribers.MobileSubscribers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;



import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

@Service
@AllArgsConstructor
@Slf4j
public class SubscriberService  implements UserDetailsService {

    private final SubscriberRepository subscriberRepository;
    private final PasswordEncoder passwordEncoder;

    private NamedParameterJdbcTemplate jdbcTemplate;

@Autowired
    public SubscriberService(SubscriberRepository subscriberRepository, PasswordEncoder passwordEncoder) {
        this.subscriberRepository = subscriberRepository;
        this.passwordEncoder = passwordEncoder;
    }


    Page<Subscribers> findPaginated(int pageNo, int pageSize) {
        return null;
    }

    public List<Subscribers> getSubscribers() {
       return subscriberRepository.findAll();
    }

//Search Subscribers by MSISDN
    public List<Subscribers> searchSubscribersByMsisdn(String msisdn) {
        return subscriberRepository.findByMsisdnContaining(msisdn);
    }


    //Change Plan Type



    public void addNewSubscriber(Subscribers newSubscriber) {
        Optional<Subscribers> existingSubscriberOptional = 
            subscriberRepository.findByMsisdn(newSubscriber.getMsisdn());
    
        if (existingSubscriberOptional.isPresent()) {
            throw new IllegalStateException("Number Taken");
        }
    
        Subscribers savedSubscriber = subscriberRepository.save(newSubscriber);
    
        System.out.println("New subscriber added: " + savedSubscriber);
    }
    

@Transactional
    //Delete Subscribers
    public void deleteSubscriber(Long SubscribersId){
        boolean exists = subscriberRepository.existsById(SubscribersId);
        if (!exists){
            throw new IllegalStateException(
                "Subscribers with id " + SubscribersId + "does not exists");
        }
        subscriberRepository.deleteById(SubscribersId);
    }

    public void deleteAllSubscribers(){
        subscriberRepository.deleteAll();
    }


    //Return All Subscribers

    public List<Subscribers> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    @Transactional
    public Subscribers getSubscriberByMsisdn(String msisdn) {
        Optional<Subscribers> subscriber = subscriberRepository.findByMsisdn(msisdn);
        return subscriber.orElse(null);
    }


    public void updateSubscriber(Subscribers subscriber) {
        subscriberRepository.save(subscriber);
    }


    public Subscribers getSubscriberById(Long id) {
        Optional<Subscribers> subscriberOptional = subscriberRepository.findById(id);
        return subscriberOptional.orElse(null);
    }


    public Subscribers getSubscriberId(Long id) {
      return subscriberRepository.findById(id).get();
    }


    public boolean isMSISDNAlreadyExists(String msisdn) {
        // Implement the logic to check if a subscriber with the given MSISDN already exists
        Optional<Subscribers> existingSubscriber = subscriberRepository.findByMsisdn(msisdn);
        return existingSubscriber.isPresent();
    }

    public Subscribers findById(Long id) {
                return subscriberRepository.findById(id).get();

            }

    public long countSubscribers(){
        return subscriberRepository.count();
    }

    public long countSubscribersPrepaid() {
        return subscriberRepository.countByServiceType(ServiceType.MobilePrepaid);

    }

    public long countSubscribersPostpaid() {
        return subscriberRepository.countByServiceType(ServiceType.MobilePostpaid);

    }

    public Subscribers findByEmail(String email) {
        Optional<Subscribers> sub = subscriberRepository.findByEmail(email);
        if(sub.isEmpty()) return null;

        return sub.get();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

