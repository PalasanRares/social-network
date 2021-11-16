//package repository.file;
//
//import domain.Friendship;
//import domain.Tuple;
//import domain.User;
//import validator.FriendshipValidator;
//import validator.Validator;
//import validator.exception.DuplicateFriendshipException;
//import validator.exception.ValidationException;
//
///**
// * Friendship repository which stores Friendship objects inside file and memory
// */
//public class FriendshipInFileRepository extends InFileRepository<Tuple<User, User>, Friendship> {
//    /**
//     * Creates an entity of type FriendshipInFileRepository
//     * @param fileName file from which data will be read
//     */
//    public FriendshipInFileRepository(String fileName) {
//        super(new FriendshipValidator(), fileName);
//    }
//
//    @Override
//    public String createEntityAsString(Friendship entity) {
//        return entity.getId().getFirst() + "," + entity.getId().getSecond();
//    }
//
//    @Override
//    public Friendship createEntityFromString(String string) {
//        String[] attributes = string.split(",");
//        Integer id1 = Integer.parseInt(attributes[0]);
//        Integer id2 = Integer.parseInt(attributes[1]);
//        return new Friendship(new Tuple<>(id1, id2));
//    }
//
//}
