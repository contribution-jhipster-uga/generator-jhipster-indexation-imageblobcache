<div align="center">
  <a href="https://www.jhipster.tech/">
    <img src="https://github.com/jhipster/jhipster-artwork/blob/master/logos/JHipster%20RGB-small100x25px.png?raw=true">
  </a>
</div>

# generator-jhipster-imageBlobIndexation

[![NPM version][npm-image]][npm-url] [![Build Status][github-actions-image]][github-actions-url] [![Dependency Status][daviddm-image]][daviddm-url]

# Introduction

This is a [JHipster](https://www.jhipster.tech/) module, that is meant to be used in a JHipster application.  
This module allows you to use image Blob type with a full text indexation by Elasticsearch. This indexation is based on a treatment provide by ImageAI, Apache Tika (Metadata, OCR).  
This module includes cache management available at : [ImageBlobCache](https://github.com/contribution-jhipster-uga/generator-jhipster-imageblobcache).

# Prerequisites

As this is a [JHipster](https://www.jhipster.tech/) module, we expect you have JHipster and its related tools already installed:

- [Installing JHipster](https://www.jhipster.tech/installation/)
- [Installing pip3 for Python3](https://www.python.org/)

```bash
sudo apt install python3-pip
```

- [Installing opencv-python](https://pypi.org/project/opencv-python/)

```bash
pip3 install opencv-python
```

- [Installing Tensorflow](https://www.tensorflow.org/)

```bash
pip3 install keras tensorflow==1.14.0
```

- [Installing ImageAI](https://github.com/OlafenwaMoses/ImageAI)

```bash
pip3 install imageai
```

- [Installing Tesseract Ocr](https://github.com/tesseract-ocr/)

```bash
sudo apt-get install tesseract-ocr
```

> This module works only with Elasticsearch and Angular project, under Linux

# Installation

## With NPM

To install this module:

```bash
npm install -g generator-jhipster-imageBlobIndexation
```

To update this module:

```bash
npm update -g generator-jhipster-imageBlobIndexation
```

## With Yarn

To install this module:

```bash
yarn global add generator-jhipster-imageBlobIndexation
```

To update this module:

```bash
yarn global upgrade generator-jhipster-imageBlobIndexation
```

# Usage

To run the module on a JHipster generated application:

```bash
yo jhipster-imageblobindexation
```

## Image Module

### Photo entity add to navbar

<div align="center">
    <img src="https://github.com/contribution-jhipster-uga/generator-jhipster-indexation-imageblobcache/blob/master/images/navbar-photo-entity.png">
</div>

### Global view of photo entities with cache

<div align="center">
    <img src="https://github.com/contribution-jhipster-uga/generator-jhipster-indexation-imageblobcache/blob/master/images/global-view-photo-entity.png">
</div>

### Form to edit and save entity

<div align="center">
    <img src="https://github.com/contribution-jhipster-uga/generator-jhipster-indexation-imageblobcache/blob/master/images/save-photo-entity.png">
</div>

### Indexation with elasticsearch

<div align="center">
    <img src="https://github.com/contribution-jhipster-uga/generator-jhipster-indexation-imageblobcache/blob/master/images/elasticsearch-photo-entity.png">
</div>

## Examples

### Example with Text as image

<div align="center">
    <img src="https://github.com/contribution-jhipster-uga/generator-jhipster-indexation-imageblobcache/blob/master/images/example1-indexation.png">
</div>

### Example with ImageAI Detection Object

<div align="center">
  <a href="https://github.com/OlafenwaMoses/ImageAI">
    <img src="https://github.com/contribution-jhipster-uga/generator-jhipster-indexation-imageblobcache/blob/master/images/example2-indexation.png">
  </a>
</div>

# TODO

- Permettre l'indexation multi-langues:
  - Utilisation d'autres fichiers [Tessdata](https://github.com/tesseract-ocr/tessdata) pour l'extraction de textes dans les images.
  - Traduire la sortie fournit par la bibliothèque ImageAI, qui est uniquement en anglais
- Adapter le module avec l'outil de build Gradle
- Adapter le module pour React
- Adapter le module pour Windows (Utilisation de wget pour le téléchargement des réseaux de neurones non compatible avec Windows)
- Tester d'autres réseaux de neurones de la bibliothèque ImageAI
- Comparer les performances de ces réseaux de neurones
- Ajouter des tests fonctionnels
- Commenter les différentes fonctions du module

# License

Apache-2.0 © [Contribution UGA](https://github.com/contribution-jhipster-uga/generator-jhipster-indexation-imageblobcache/blob/master/LICENSE)

[npm-image]: https://img.shields.io/npm/v/generator-jhipster-imageBlobIndexation.svg
[npm-url]: https://npmjs.org/package/generator-jhipster-imageBlobIndexation
[github-actions-image]: https://github.com/maximelordey/generator-jhipster-imageBlobIndexation/workflows/Build/badge.svg
[github-actions-url]: https://github.com/maximelordey/generator-jhipster-imageBlobIndexation/actions
[daviddm-image]: https://david-dm.org/maximelordey/generator-jhipster-imageBlobIndexation.svg?theme=shields.io
[daviddm-url]: https://david-dm.org/maximelordey/generator-jhipster-imageBlobIndexation

### Versionning

versionning standards: v(**Major**).(**Minor**).(**bugfix**)

current version: **v2.0.1**
