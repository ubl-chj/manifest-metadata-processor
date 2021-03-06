/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ubleipzig.metadata.producer.doc;

import static org.slf4j.LoggerFactory.getLogger;
import static org.xmlbeam.XBProjector.Flags.TO_STRING_RENDERS_XML;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.xmlbeam.XBProjector;

/**
 * MetsManifestBuilder.
 *
 * @author christopher-johnson
 */
public final class MetsManifestBuilder {

    private static Logger logger = getLogger(MetsManifestBuilder.class);

    private MetsManifestBuilder() {
    }

    /**
     * getMetsFromFile.
     *
     * @param xml String
     * @return XBProjector
     */
    public static MetsData getMetsFromString(final String xml) {
            final XBProjector projector = new XBProjector(TO_STRING_RENDERS_XML);
            return projector.projectXMLString(xml, MetsData.class);
    }

    /**
     * @param url File
     * @return MetsData
     */
    static MetsData getMetsFromFile(final File url) {
        try {
            final XBProjector projector = new XBProjector(TO_STRING_RENDERS_XML);
            return projector.io().file(url).read(MetsData.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot Read XML: " + e.getMessage());
        }
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getManifestTitle(final MetsData mets) {
        return mets.getManifestTitle().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getUrnReference(final MetsData mets) {
        return mets.getManuscriptIdByType("urn").orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    static String getSubtitle(final MetsData mets) {
        return mets.getSubtitle().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getAttribution(final MetsData mets) {
        return mets.getAttribution().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getPresentationUri(final MetsData mets) {
        return mets.getPresentationUri().orElse("");
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getLogo(final MetsData mets) {
        return mets.getLogo().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getManuscriptType(final MetsData mets) {
        return mets.getManuscriptType().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @param idType String
     * @return String
     */
    public static String getManuscriptIdByType(final MetsData mets, final String idType) {
        return mets.getManuscriptIdByType(idType).orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getMedium(final MetsData mets) {
        return mets.getMedium().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getMaterial(final MetsData mets) {
        return mets.getMaterial().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getExtent(final MetsData mets) {
        return mets.getExtent().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getDimension(final MetsData mets) {
        return mets.getDimension().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getLanguage(final MetsData mets) {
        return mets.getLanguage().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getLocation(final MetsData mets) {
        return mets.getLocation().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getRecordIdentifier(final MetsData mets) {
        return mets.getRecordIdentifier().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getDateCreated(final MetsData mets) {
        return mets.getDateCreated().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return List
     */
    public static List<String> getNoteTypes(final MetsData mets) {
        return mets.getNoteTypes();
    }

    /**
     * @param mets MetsData
     * @param type String
     * @return String
     */
    public static String getNotesByType(final MetsData mets, final String type) {
        return mets.getNotesByType(type).orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getCensus(final MetsData mets) {
        return mets.getCensus().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getCollection(final MetsData mets) {
        return mets.getCollection().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getCallNumber(final MetsData mets) {
        return mets.getCallNumber().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getOwner(final MetsData mets) {
        return mets.getOwner().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getAuthor(final MetsData mets) {
        return mets.getAuthor().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getPlace(final MetsData mets) {
        return mets.getPlace().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getDate(final MetsData mets) {
        return mets.getDate().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getPublisher(final MetsData mets) {
        return mets.getPublisher().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getPhysState(final MetsData mets) {
        return mets.getPhysState().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return String
     */
    public static String getNote(final MetsData mets) {
        return mets.getNote().orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @return List
     */
    public static List<String> getPhysicalDivs(final MetsData mets) {
        return mets.getPhysicalDivs();
    }

    /**
     * @param mets MetsData
     * @param div String
     * @return String
     */
    public static String getOrderLabelForDiv(final MetsData mets, final String div) {
        return mets.getOrderLabelForDiv(div).orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @param div String
     * @return String
     */
    public static String getFileIdForDiv(final MetsData mets, final String div) {
        return mets.getFileIdForDiv(div).orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @param file String
     * @return String
     */
    public static String getHrefForFile(final MetsData mets, final String file) {
        return mets.getHrefForFile(file).orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @param file String
     * @return String
     */
    public static String getMimeTypeForFile(final MetsData mets, final String file) {
        return mets.getMimeTypeForFile(file).orElse("").trim();
    }

    /**
     * @param mets MetsData
     * @param id String
     * @return Logical
     */
    public static MetsData.Logical getLogicalLastDescendent(final MetsData mets, final String id) {
        return mets.getLogicalLastDescendent(id);
    }

    /**
     * @param mets MetsData
     * @param id String
     * @return List
     */
    public static List<MetsData.Logical> getLogicalLastParent(final MetsData mets, final String id) {
        return mets.getLogicalLastParent(id);
    }

    /**
     * @param mets MetsData
     * @return List
     */
    public static List<MetsData.Logical> getTopLogicals(final MetsData mets) {
        return mets.getTopLogicals();
    }

    /**
     * @param mets MetsData
     * @param id String
     * @return List
     */
    public static List<MetsData.Logical> getLogicalLastChildren(final MetsData mets, final String id) {
        return mets.getLogicalLastChildren(id);
    }

    /**
     * @param mets MetsData
     * @param id String
     * @return String
     */
    public static String getLogicalLabel(final MetsData mets, final String id) {
        final ResourceBundle labels = ResourceBundle.getBundle("LabelsBundle", Locale.GERMAN);
        final String type = mets.getLogicalType(id).orElse("");
        final String label;
        if (labels.containsKey(type)) {
            final String translatedType = labels.getString(type);
            label = mets.getLogicalLabel(id).orElse(translatedType);
        } else {
            label = mets.getLogicalLabel(id).orElse("");
            logger.warn("Missing Resource Bundle Mapping for Key \"{}\"", type);
        }
        return label;
    }

    /**
     * @param mets MetsData
     * @param id String
     * @return String
     */
    public static String getLogicalType(final MetsData mets, final String id) {
        return mets.getLogicalType(id).orElse("");
    }

    /**
     * @param mets MetsData
     * @return List
     */
    public static List<MetsData.Xlink> getXlinks(final MetsData mets) {
        return mets.getXlinks();
    }


}
