package de.u808.simpleinquest.indexer.impl;/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *      http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */import java.io.File;import java.io.FileInputStream;import java.io.FileNotFoundException;import java.util.ArrayList;import java.util.Iterator;import java.util.List;import org.apache.commons.logging.Log;import org.apache.commons.logging.LogFactory;import org.jaxen.JaxenException;import org.jaxen.SimpleNamespaceContext;import org.jaxen.jdom.JDOMXPath;import org.jdom.Attribute;import org.jdom.CDATA;import org.jdom.Comment;import org.jdom.Element;import org.jdom.EntityRef;import org.jdom.Namespace;import org.jdom.ProcessingInstruction;import org.jdom.Text;import de.u808.common.LiusUtils;/** * Classe se basant sur JDOM et XPATH pour indexer des fichiers XML. <br/><br/> * Class based on JDOM and XPATH for indexing XML files. * * @author Rida Benjelloun (ridabenjelloun@gmail.com) */public class XmlFileIndexer extends AbstractIndexer {	private SimpleNamespaceContext nsc = new SimpleNamespaceContext();	//static Logger logger = Logger.getRootLogger();	static Log log = LogFactory.getLog(XmlFileIndexer.class);	public String getContent() {		try {			return concatOccurance(LiusUtils.parse(new FileInputStream(this.file)), "//*", "");		} catch (FileNotFoundException e) {			log.error("Exception while indexing XML file " + file.getPath(), e);		}		return "";	}	/**	 *	 * M�thode permettant de concat�ner les occurences multiples d'un �l�ment	 * qui	 *	 * vont �tre stock�es dans le m�me document Lucene.	 *	 * <br/><br/>	 *	 * Method that concatenates multiple hist of an element which will be saved	 * in	 *	 * the same Lucene document.	 *	 */	public String concatOccurance(Object xmlDoc, String xpath, String concatSep) {		StringBuilder chaineConcat = new StringBuilder();		//StringBuffer chaineConcat = new StringBuffer();		try {			JDOMXPath xp = new JDOMXPath(xpath);			xp.setNamespaceContext(nsc);			List ls = xp.selectNodes(xmlDoc);			Iterator i = ls.iterator();			int j = 0;			while (i.hasNext()) {				j++;				String text = "";				Object obj = (Object) i.next();				if (obj instanceof Element) {					Element elem = (Element) obj;					text = elem.getText().trim();				} else if (obj instanceof Attribute) {					Attribute att = (Attribute) obj;					text = att.getValue().trim();				} else if (obj instanceof Text) {					Text txt = (Text) obj;					text = txt.getText().trim();				} else if (obj instanceof CDATA) {					CDATA cdata = (CDATA) obj;					text = cdata.getText().trim();				} else if (obj instanceof Comment) {					Comment com = (Comment) obj;					text = com.getText().trim();				} else if (obj instanceof ProcessingInstruction) {					ProcessingInstruction pi = (ProcessingInstruction) obj;					text = pi.getData().trim();				} else if (obj instanceof EntityRef) {					EntityRef er = (EntityRef) obj;					text = er.toString().trim();				}				if (text != "") {					if (ls.size() == 1) {						chaineConcat.append(text);						return chaineConcat.toString().trim();					} else {						if (ls.size() == j)							chaineConcat.append(text);						else							chaineConcat.append(text + " " + concatSep + " ");					}				}			}		} catch (JaxenException j) {			log.error(j.getMessage());		}		return chaineConcat.toString().trim();	}//	/**//	 * Retourne une collection contenant les champs avec les valeurs � indexer//	 * comme par exemple: le texte integral, titre etc. <br/><br/> Returns a//	 * collection containing the fields with the values to index; like : full//	 * text, title, etc.//	 */////	public Collection getPopulatedLiusFields() {//		Document xmlDoc = LiusUtils.parse(getStreamToIndex());//		return getPopulatedLiusFields(xmlDoc, getLiusConfig()//				.getXmlFileFields());////	}////	public Collection getPopulatedLiusFields(Object xml,//			Collection liusXmlFields) {//		List documentNs = null;//		Map hm = null;//		boolean nsTrouve = false;//		boolean isMap = false;//		Collection resColl = new ArrayList();//		if (xml instanceof org.jdom.Document) {//			documentNs = getAllDocumentNs((org.jdom.Document) xml);//		}//		Iterator itColl = liusXmlFields.iterator();//		while (itColl.hasNext()) {//			Object colElem = itColl.next();//			if (colElem instanceof Map) {//				isMap = true;//				hm = (Map) colElem;//				for (int j = 0; j < documentNs.size(); j++) {//					Collection liusFields = (Collection) hm.get(documentNs//							.get(j));//					if (liusFields != null) {//						nsTrouve = true;//						extractDataFromElements(xml, liusFields, resColl);//					}//				}//			}//			if (nsTrouve == false && (colElem instanceof Map)) {//				extractDataFromElements(xml, (Collection) hm.get("default"),//						resColl);//			}//		}//		if (isMap == false)//			extractDataFromElements(xml, liusXmlFields, resColl);//		return resColl;//	}////	private void extractDataFromElements(Object xmlDoc, Collection liusFields,//			Collection resColl) {//		Iterator it = liusFields.iterator();//		while (it.hasNext()) {//			Object field = it.next();//			if (field instanceof LiusField) {//				LiusField lf = (LiusField) field;//				if (lf.getOcurSep() != null) {//					String cont = concatOccurance(xmlDoc, lf.getXpathSelect(),//							lf.getOcurSep());//					lf.setValue(cont);//					resColl.add(lf);//				} else {//					try {//						JDOMXPath xp = new JDOMXPath(lf.getXpathSelect());//						xp.setNamespaceContext(nsc);//						List selectList = xp.selectNodes(xmlDoc);//						Iterator i = selectList.iterator();//						while (i.hasNext()) {//							LiusField lfoccur = new LiusField();//							BeanUtils.copyProperties(lfoccur, lf);//							Object selection = (Object) i.next();//							if (selection instanceof Element) {//								Element elem = (Element) selection;//								if (elem.getText().trim() != null//										&& elem.getText().trim() != "") {//									lfoccur.setValue(elem.getText());//									resColl.add(lfoccur);//								}//							} else if (selection instanceof Attribute) {//								Attribute att = (Attribute) selection;//								lfoccur.setValue(att.getValue());//								resColl.add(lfoccur);//							} else if (selection instanceof Text) {//								Text text = (Text) selection;//								lfoccur.setValue(text.getText());//								resColl.add(lfoccur);//							} else if (selection instanceof CDATA) {//								CDATA cdata = (CDATA) selection;//								lfoccur.setValue(cdata.getText());//								resColl.add(lfoccur);//							} else if (selection instanceof Comment) {//								Comment com = (Comment) selection;//								lfoccur.setValue(com.getText());//								resColl.add(lfoccur);//							} else if (selection instanceof ProcessingInstruction) {//								ProcessingInstruction pi = (ProcessingInstruction) selection;//								lfoccur.setValue(pi.getData());//								resColl.add(lfoccur);//							} else if (selection instanceof EntityRef) {//								EntityRef er = (EntityRef) selection;//								lfoccur.setValue(er.toString());//								resColl.add(lfoccur);//							}//						}//					} catch (JaxenException e) {//						log.error(e.getMessage());//					} catch (InvocationTargetException ex) {//						log.error(ex.getMessage());//					} catch (IllegalAccessException ex) {//						log.error(ex.getMessage());//					}////				}//			} else {//				resColl.add(field);//			}//		}////	}	public List getAllDocumentNs(org.jdom.Document doc) {		List ls = new ArrayList();		processChildren(doc.getRootElement(), ls);		return ls;	}	private boolean exist(List nsLs, String nsUri) {		if (nsLs.isEmpty())			return false;		for (int i = 0; i < nsLs.size(); i++) {			if (((String) nsLs.get(i)).equals(nsUri)) {				return true;			}		}		return false;	}	private void processChildren(Element elem, List ns) {		Namespace nsCourent = (Namespace) elem.getNamespace();		String nsUri = (nsCourent.getURI());		if (!exist(ns, nsUri)) {			ns.add(nsUri.trim());			nsc.addNamespace(nsCourent.getPrefix(), nsCourent.getURI());		}		List additionalNs = elem.getAdditionalNamespaces();		if (!additionalNs.isEmpty())			copyNsList(additionalNs, ns);		if (elem.getChildren().size() > 0) {			List elemChildren = elem.getChildren();			for (int i = 0; i < elemChildren.size(); i++) {				processChildren((Element) elemChildren.get(i), ns);			}		}	}	private void copyNsList(List nsElem, List nsRes) {		for (int i = 0; i < nsElem.size(); i++) {			Namespace ns = (Namespace) nsElem.get(i);			nsc.addNamespace(ns.getPrefix(), ns.getURI());			nsRes.add(ns.getURI().trim());		}	}	@Override	public void setContentsFild(File file) {		// TODO Auto-generated method stub			}}