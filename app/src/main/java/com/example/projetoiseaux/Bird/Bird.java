package com.example.projetoiseaux.Bird;

import com.example.projetoiseaux.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Bird {
    public static ArrayList<Bird> FULL_LIST = new ArrayList<>(
            Arrays.asList(
                    new Bird("European robin", "Grey", 1,R.drawable.rouge_gorge_familier,"The European robin (Erithacus rubecula), known simply as the robin or robin redbreast in Ireland and Britain, is a small insectivorous passerine bird that belongs to the chat subfamily of the Old World flycatcher family. About 12.5–14.0 cm (5.0–5.5 inches) in length, the male and female are similar in colouration, with an orange breast and face lined with grey, brown upper-parts and a whitish belly. It is found across Europe, east to Western Siberia and south to North Africa; it is sedentary in most of its range except the far north."),
                    new Bird("Eurasian blue tit", "Blue", 1, R.drawable.mesange, "The Eurasian blue tit (Cyanistes caeruleus)[2] is a small passerine bird in the tit family, Paridae. It is easily recognisable by its blue and yellow plumage and small size. Eurasian blue tits, usually resident and non-migratory birds, are widespread and a common resident breeder throughout temperate and subarctic Europe and the western Palearctic in deciduous or mixed woodlands with a high proportion of oak. They usually nest in tree holes, although they easily adapt to nest boxes where necessary. Their main rival for nests and in the search for food is the larger and more common great tit."),
                    new Bird("Common blackbird", "Black", 2, R.drawable.merle_noir, "The common blackbird (Turdus merula) is a species of true thrush. It is also called the Eurasian blackbird (especially in North America, to distinguish it from the unrelated New World blackbirds),[2] or simply the blackbird where this does not lead to confusion with a similar-looking local species. It breeds in Europe, Asiatic Russia, and North Africa, and has been introduced to Australia and New Zealand.[3] It has a number of subspecies across its large range; a few of the Asian subspecies are sometimes considered to be full species. Depending on latitude, the common blackbird may be resident, partially migratory, or fully migratory."),
                    new Bird("House sparrow", "Maroon", 1, R.drawable.moineau_domestique, "The house sparrow (Passer domesticus) is a bird of the sparrow family Passeridae, found in most parts of the world. It is a small bird that has a typical length of 16 cm (6.3 in) and a mass of 24–39.5 g (0.85–1.39 oz). Females and young birds are coloured pale brown and grey, and males have brighter black, white, and brown markings. One of about 25 species in the genus Passer, the house sparrow is native to most of Europe, the Mediterranean Basin, and a large part of Asia. Its intentional or accidental introductions to many regions, including parts of Australasia, Africa, and the Americas, make it the most widely distributed wild bird."),
                    new Bird("Common chaffinch", "Maroon", 1, R.drawable.pinson_des_arbres, "The common chaffinch or simply the chaffinch (Fringilla coelebs) is a common and widespread small passerine bird in the finch family. The male is brightly coloured with a blue-grey cap and rust-red underparts. The female is more subdued in colouring, but both sexes have two contrasting white wing bars and white sides to the tail. The male bird has a strong voice and sings from exposed perches to attract a mate. The chaffinch breeds in much of Europe, across the Palearctic to Siberia and in northwestern Africa. The female builds a nest with a deep cup in the fork of a tree. The clutch is typically four or five eggs, which hatch in about 13 days."),
                    new Bird("Solitary eagle","Black",4, R.drawable.aigle_solitaire, "The solitary eagle is native to Mexico and Central and South America. It is found in mountainous or hilly forests, at elevations between 600 m and 2,200 m. The frequent reports from lowlands are usually misidentifications of another species, usually the common black hawk or great black hawk; no reports from lowlands have been confirmed. It is rare in all areas of its range and poorly known. Very little is known about its diet, other than that it appears to have often been predating large snakes and one adult pair was seen hunting deer fawns. The remains of a chachalaca were noted in one nest."),
                    new Bird("California gull","Grey",3,R.drawable.mouette_californienne,"Adults are similar in appearance to the herring gull, but have a smaller yellow bill with a black ring, yellow legs, brown eyes and a more rounded head. The body is mainly white with grey back and upper wings. They have black primaries with white tips. Immature birds are also similar in appearance to immature herring gulls, with browner plumage than immature ring-billed gulls. Length can range from 46 to 55 cm (18 to 22 in), the wingspan 122–137 cm (48–54 in) and body mass can vary from 430 to 1,045 g (0.948 to 2.304 lb)."),
                    new Bird("Grey kestrel","Grey",3,R.drawable.faucon_gris,"It is a fairly small, stocky kestrel with a large, flat-topped head and fairly short wings that don't reach past the tip of the tail when at rest. It is 28–33 cm long with a wingspan of 58–72 cm and a weight of up to 300 grams. The female is 4-11% larger and 5-11% heavier than the male. The plumage of the adult is uniformly dark grey apart from darker wingtips, faint dark streaking on the body and slightly barred flight feathers. The feet and cere are yellow and there is bare yellow skin around the eye. The most similar species is the sooty falcon which has a more rounded head, long wings extending past the tail and less yellow around the eye."),
                    new Bird("Mountain bluebird","Blue",1,R.drawable.merle_bleu,"he mountain bluebird (Sialia currucoides) is a small migratory thrush that is found in mountainous districts of western North America. It has a light underbelly and black eyes. Adult males have thin bills and are bright turquoise-blue and somewhat lighter underneath. Adult females have duller blue wings and tail, grey breast, grey crown, throat and back. In fresh fall plumage, the female's throat and breast are tinged with red-orange, brownish near the flank contrasting with white tail underparts. Their call is a thin 'few'; while their song is warbled high 'chur chur'. It is the state bird of Idaho and Nevada.")
            )
    );

    public static ArrayList<String> COLORS;

    public static String[] SIZES;


    public static HashMap<String, ArrayList<ArrayList<Bird>>> sortedList;

    private final String name;
    private final String color;
    private final int size;
    private final int picture;
    private final String description;

    public Bird(String name, String color, int size, int picture, String description){
        this.name = name;
        this.color = color;
        this.size = size;
        this.picture = picture;
        this.description = description;
        sortList();
        Objects.requireNonNull(sortedList.get(color)).get(size).add(this);
    }

    public void sortList(){
        if (sortedList == null) {
            sortedList = new HashMap<>();
             COLORS = new ArrayList<>(
                    Arrays.asList("Any", "Red", "Blue", "Green", "Yellow", "Purple", "Cyan", "Magenta", "Maroon", "Grey", "Black", "White")
            );
            SIZES = new String[]{"Any", "Very Small", "Small", "Medium", "Big", "Very Big"};
            System.out.println(COLORS);
            System.out.println(Arrays.toString(SIZES));
            for (String color : COLORS) {
                ArrayList<ArrayList<Bird>> sizeLists = new ArrayList<>();
                for (int i = 0; i < SIZES.length; i++)
                    sizeLists.add(new ArrayList<>());
                sortedList.put(color, sizeLists);
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public int getPicture() {
        return picture;
    }

    public String getDescription() { return description; }
}
