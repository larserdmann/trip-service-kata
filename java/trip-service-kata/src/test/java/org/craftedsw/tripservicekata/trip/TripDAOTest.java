package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripDAOTest {

    @Test
    public void test() {

        // Arrange
        TripDAO dao = new TripDAO();
        User user = new User();

        // Act / Assert
        assertThrows(Exception.class, () -> dao.findTripsByUser2(user));
    }

}
