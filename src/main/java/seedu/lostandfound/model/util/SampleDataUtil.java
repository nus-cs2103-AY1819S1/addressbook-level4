package seedu.lostandfound.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.ReadOnlyArticleList;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.article.Description;
import seedu.lostandfound.model.article.Email;
import seedu.lostandfound.model.article.Name;
import seedu.lostandfound.model.article.Phone;
import seedu.lostandfound.model.image.Image;
import seedu.lostandfound.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ArticleList} with sample data.
 */
public class SampleDataUtil {
    public static Article[] getSampleArticles() {
        return new Article[] {
            new Article(new Name("Nike Wallet"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Description("Found at Blk 30 Geylang Street 29, #06-40"), new Name("Alex Yeoh"),
                    new Name("Not Claimed"), Boolean.FALSE,
                getTagSet("Black")),
            new Article(new Name("Nike bag"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Description("Found at Blk 30 Lorong 3 Serangoon Gardens, #07-18"), Image.DEFAULT, new Name("Bernice Yu"),
                    new Name("Not Claimed"), Boolean.FALSE, getTagSet("Scratched", "Black")),
            new Article(new Name("Fitbit"), new Phone("93210283"), Image.DEFAULT, new Email("charlotte@example.com"),
                new Description("Found at Blk 11 Ang Mo Kio Street 74, #11-04"), new Name("Charlotte Oliveiro"),
                    new Name("Not Claimed"), Boolean.FALSE, Image.DEFAULT, getTagSet("New")),
            new Article(new Name("Casio watch"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Description("Found at Blk 436 Serangoon Gardens Street 26, #16-43"), new Name("David Li"),
                    new Name("Not Claimed"), Boolean.FALSE, Image.DEFAULT, getTagSet("Red")),
            new Article(new Name("Bluetooth speaker"), new Phone("92492021"), new Email("irfan@example.com"),
                new Description("Found at Blk 47 Tampines Street 20, #17-35"), new Name("Irfan Ibrahim"),
                    new Name("Not Claimed"), Boolean.FALSE, Image.DEFAULT, getTagSet("Blue")),
            new Article(new Name("IPhone 7"), new Phone("92624417"), new Email("royb@example.com"),
                new Description("Found at Blk 45 Aljunied Street 85, #11-31"), new Name("Roy Balakrishnan"),
                    new Name("Not Claimed"), Boolean.FALSE, Image.DEFAULT, getTagSet("Gold"))
        };
    }

    public static ReadOnlyArticleList getSampleArticleList() {
        ArticleList sampleAb = new ArticleList();
        for (Article sampleArticle : getSampleArticles()) {
            sampleAb.addArticle(sampleArticle);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
