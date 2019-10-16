package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

    private UserSession session = UserSession.getInstance();
    private TripDAO dao;

    public TripService(TripDAO dao) {
        this.dao = dao;
    }

    public void setSession(final UserSession session) {
        this.session = session;
    }

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        List<Trip> tripList = new ArrayList<Trip>();
        User loggedUser = session.getLoggedUser();
        boolean isFriend = false;

        if (loggedUser == null)
            throw new UserNotLoggedInException();

        for (User friend : user.getFriends()) {
            if (friend.equals(loggedUser)) {
                isFriend = true;
                break;
            }
        }
        if (isFriend) {
            tripList = findTripsByUser(user);
        }
        return tripList;
    }

    List<Trip> findTripsByUser(final User user) {
        return dao.findTripsByUser2(user);
    }

}
