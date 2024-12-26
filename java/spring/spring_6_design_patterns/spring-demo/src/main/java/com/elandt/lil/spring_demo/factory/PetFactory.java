package com.elandt.lil.spring_demo.factory;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * This is the actual factory for the objects that implement Pet
 */
@Component
public class PetFactory {

    public Pet createPet(String animalType) {
        if (!StringUtils.hasLength(animalType)) {
            throw new UnsupportedOperationException("Animal type must be specified");
        }

        switch (animalType.toLowerCase()) {
            case "dog":
                return new Dog();
            case "cat":
                return new Cat();
            default:
                throw new UnsupportedOperationException("Unknown animal type");
        }
    }
}
