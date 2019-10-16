package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.List;

import static java.util.Collections.emptyList;

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
		User loggedUser = session.getLoggedUser();

        if (loggedUser == null)
            throw new UserNotLoggedInException();

		if (loggedUser.isFriend(user)) {
			return dao.findTripsBy(user);
        }

		return emptyList();
    }

}
